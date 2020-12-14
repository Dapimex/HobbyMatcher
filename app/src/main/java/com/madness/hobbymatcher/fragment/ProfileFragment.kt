package com.madness.hobbymatcher.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.madness.hobbymatcher.HobbyMatcherApplication
import com.madness.hobbymatcher.MainActivity
import com.madness.hobbymatcher.R
import com.madness.hobbymatcher.adapter.ActivitiesAdapter
import com.madness.hobbymatcher.loginmanager.security.LoginManager
import com.madness.hobbymatcher.networking.ActivityService
import com.madness.hobbymatcher.networking.UserService
import com.madness.hobbymatcher.networking.response.Activities
import com.madness.hobbymatcher.networking.response.WhoAmI
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ProfileFragment : Fragment() {

    @Inject
    lateinit var activityService: ActivityService

    @Inject
    lateinit var userService: UserService

    @Inject
    lateinit var loginManager: LoginManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (HobbyMatcherApplication.APPLICATION as HobbyMatcherApplication).appComponent.inject(this)
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_logout.setOnClickListener(this::onLogoutButtonClick)

        view.createdActivitiesRecyclerView.adapter = ActivitiesAdapter { id -> deleteActivity(id) }

        userService.whoAmI().enqueue(object : Callback<WhoAmI> {
            override fun onFailure(call: Call<WhoAmI>, t: Throwable) {
                Toast.makeText(
                    context,
                    "Failed to fetch my username: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(call: Call<WhoAmI>, response: Response<WhoAmI>) {
                if (response.body() != null) {
                    profileUserTextView.text = response.body()!!.username
                }
            }
        })

        updateData()
    }

    @Suppress("UNUSED_PARAMETER")
    fun onLogoutButtonClick(button: View) {
        loginManager.logout()

        val intent = Intent(requireActivity(), MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun updateData() {
        (requireView().createdActivitiesRecyclerView.adapter as ActivitiesAdapter).clearActivities()

        activityService.getMyActivities().enqueue(object : Callback<Activities> {
            override fun onFailure(call: Call<Activities>, t: Throwable) {
                Toast.makeText(
                    context,
                    "Failed to fetch my activities: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(
                call: Call<Activities>,
                response: Response<Activities>
            ) {
                if (response.body() != null) {
                    (view!!.createdActivitiesRecyclerView.adapter as ActivitiesAdapter).addMyActivities(
                        response.body()!!.activities
                    )
                }
            }

        })

        activityService.getJoinedActivities().enqueue(object : Callback<Activities> {
            override fun onFailure(call: Call<Activities>, t: Throwable) {
                Toast.makeText(
                    context,
                    "Failed to fetch joined activities: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(
                call: Call<Activities>,
                response: Response<Activities>
            ) {
                if (response.body() != null) {
                    (view!!.createdActivitiesRecyclerView.adapter as ActivitiesAdapter).addJoinedActivities(
                        response.body()!!.activities
                    )
                }
            }
        })
    }

    private fun deleteActivity(activityId: Int) {
        activityService.deleteActivity(activityId).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    context,
                    "Failed to delete activity: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                updateData()
            }
        })
    }
}