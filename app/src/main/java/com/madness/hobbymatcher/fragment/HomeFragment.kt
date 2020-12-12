package com.madness.hobbymatcher.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.madness.hobbymatcher.R
import com.madness.hobbymatcher.adapter.InviteActivityAdapter
import com.madness.hobbymatcher.networking.ActivityService
import com.madness.hobbymatcher.networking.HobbyMatcherServiceProvider
import com.madness.hobbymatcher.networking.InvitationService
import com.madness.hobbymatcher.networking.response.Activities
import com.madness.hobbymatcher.networking.response.Activity
import com.madness.hobbymatcher.networking.response.Invitation
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

sealed class InviteActivity {
    data class InviteType(val invite: Invitation): InviteActivity()
    data class ActivityType(val activity: Activity): InviteActivity()
}

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity_list.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = InviteActivityAdapter()
        }
        fillActivities()
    }

    private fun fillActivities() {
        val invitationService = HobbyMatcherServiceProvider.obtain(InvitationService::class.java)
        val activityService = HobbyMatcherServiceProvider.obtain(ActivityService::class.java)

        (activity_list.adapter as InviteActivityAdapter).clearActivities()
        invitationService.getMyInvitations().enqueue(object: Callback<List<Invitation>> {

            override fun onFailure(call: Call<List<Invitation>>, t: Throwable) {
                Toast.makeText(context, "Failed to fetch invitations: ${t.message}", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<List<Invitation>>,
                response: Response<List<Invitation>>
            ) {
                (activity_list.adapter as InviteActivityAdapter).addActivities(response.body()
                    ?.map {
                        InviteActivity.InviteType(
                            it
                        )
                    }
                    ?: emptyList())
            }
        })

        activityService.getMyActivities().enqueue(object: Callback<Activities> {
            override fun onFailure(call: Call<Activities>, t: Throwable) {
                Toast.makeText(context, "Failed to fetch activities: ${t.message}", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<Activities>,
                response: Response<Activities>
            ) {
                if (response.body() != null) {
                    (activity_list.adapter as InviteActivityAdapter).addActivities(
                        response.body()!!.activities
                            .map {
                                InviteActivity.ActivityType(
                                    it
                                )
                            })
                }
            }

        })
    }

}