package com.madness.hobbymatcher.Profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.madness.hobbymatcher.HobbyMatcherApplication
import com.madness.hobbymatcher.R
import com.madness.hobbymatcher.adapter.ActivitiesAdapter
import com.madness.hobbymatcher.networking.ActivityService
import com.madness.hobbymatcher.networking.response.Activity
import kotlinx.android.synthetic.main.fragment_profile.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ProfileFragment: Fragment() {

    @Inject
    lateinit var activityService: ActivityService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (HobbyMatcherApplication.APPLICATION as HobbyMatcherApplication).appComponent.inject(this)
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activities: MutableList<Activity> = mutableListOf()

        activityService.getMyActivities().enqueue(object: Callback<List<Activity>> {
            override fun onFailure(call: Call<List<Activity>>, t: Throwable) {
                println("onError:") // Logger for now
                println(t.message)
                Toast.makeText(context, "Failed to fetch activities: ${t.message}", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<List<Activity>>,
                response: Response<List<Activity>>
            ) {
                println("onResponse:")
                println(response.code().toString() + response.message().toString())
                if (response.body() != null) {
                    activities.addAll(response.body() ?: emptyList())
                }
            }

        })

        view.createdActivitiesRecyclerView.adapter = ActivitiesAdapter(activities)
    }
}