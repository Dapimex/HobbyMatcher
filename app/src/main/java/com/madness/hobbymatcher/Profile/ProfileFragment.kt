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
import com.madness.hobbymatcher.networking.response.Activities
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

        view.createdActivitiesRecyclerView.adapter = ActivitiesAdapter()

        activityService.getMyActivities().enqueue(object: Callback<Activities> {
            override fun onFailure(call: Call<Activities>, t: Throwable) {
                Toast.makeText(context, "Failed to fetch activities: ${t.message}", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<Activities>,
                response: Response<Activities>
            ) {
                if (response.body() != null) {
                    (view.createdActivitiesRecyclerView.adapter as ActivitiesAdapter).addActivities(response.body()!!.activities)
                }
            }

        })
    }
}