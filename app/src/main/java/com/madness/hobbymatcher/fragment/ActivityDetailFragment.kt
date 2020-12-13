package com.madness.hobbymatcher.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.madness.hobbymatcher.HobbyMatcherApplication
import com.madness.hobbymatcher.R
import com.madness.hobbymatcher.adapter.ParticipantsAdapter
import com.madness.hobbymatcher.networking.ActivityService
import com.madness.hobbymatcher.networking.interceptors.CredentialsStore
import com.madness.hobbymatcher.networking.response.Activity
import kotlinx.android.synthetic.main.activity_detail.view.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ActivityDetailFragment : Fragment() {

    var activityId: Int? = null

    private val frontSdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    private val backSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

    @Inject
    lateinit var activityService: ActivityService

    @Inject
    lateinit var credentialsStore: CredentialsStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityId = requireArguments().get("id") as Int
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (HobbyMatcherApplication.APPLICATION as HobbyMatcherApplication).appComponent.inject(this)
        return inflater.inflate(R.layout.activity_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateData(view)

        view.joinButton.setOnClickListener {
            activityService.joinActivity(activityId!!).enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(context, "Failed to join activity", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    updateData(view)
                }
            })
        }
    }

    fun updateData(view: View) {
        activityService.getActivity(activityId!!).enqueue(object : Callback<Activity> {
            override fun onFailure(call: Call<Activity>, t: Throwable) {
                Toast.makeText(context, "Failed to fetch activity details", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(call: Call<Activity>, response: Response<Activity>) {
                if (response.body() != null) {
                    bind(view, response.body()!!)
                }
            }
        })
    }

    fun bind(view: View, activity: Activity) {
        view.nameTextView.text = activity.name
        view.descriptionTextView.text = activity.description
        view.locationTextView.text = activity.location
        view.startTimeTextView.text = frontSdf.format(backSdf.parse(activity.startTime))

        view.durationTextView.text = activity.duration
        view.typeTextView.text = activity.type
        if (activity.isPublic != null && activity.isPublic!!) {
            view.isPublicTextView.text = "PUBLIC"
            view.isPublicTextView.setTextColor(
                ContextCompat.getColor(
                    this.requireContext(),
                    R.color.secondaryColor
                )
            )
        } else {
            view.isPublicTextView.text = "PRIVATE"
            view.isPublicTextView.setTextColor(
                ContextCompat.getColor(
                    this.requireContext(),
                    R.color.green
                )
            )
        }

        if (activity.participants!!.any { it.username == credentialsStore.username }) {
            view.inviteButton.isEnabled = true
            view.joinButton.isEnabled = false // TODO change button to "LEAVE"
        } else {
            view.joinButton.isEnabled = true
            view.inviteButton.isEnabled = false
        }

        val owner = activity.participants!!.find { it.role == "OWNER" }
        view.ownerUsernameTextView.text = owner!!.username

        view.participantsRecyclerView.adapter = ParticipantsAdapter()

        (view.participantsRecyclerView.adapter as ParticipantsAdapter).addUsers(activity.participants!!.filter { it.role != "OWNER" }
            .map { user -> user.username })

        view.participantsRecyclerView.layoutManager = LinearLayoutManager(context)
    }

}