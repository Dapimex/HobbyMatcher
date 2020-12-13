package com.madness.hobbymatcher.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.madness.hobbymatcher.HobbyMatcherApplication
import com.madness.hobbymatcher.R
import com.madness.hobbymatcher.adapter.InviteActivityAdapter
import com.madness.hobbymatcher.networking.ActivityService
import com.madness.hobbymatcher.networking.HobbyMatcherServiceProvider
import com.madness.hobbymatcher.networking.InvitationService
import com.madness.hobbymatcher.networking.response.Activities
import com.madness.hobbymatcher.networking.response.Activity
import com.madness.hobbymatcher.networking.response.Invitation
import com.madness.hobbymatcher.networking.response.Invitations
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

sealed class InviteActivity {
    data class InviteType(val invite: Invitation): InviteActivity()
    data class ActivityType(val activity: Activity): InviteActivity()
    data class TitleType(val text: String) : InviteActivity()
}

class HomeFragment : Fragment() {

    @Inject
    lateinit var activityService: ActivityService

    @Inject
    lateinit var invitationService: InvitationService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (HobbyMatcherApplication.APPLICATION as HobbyMatcherApplication).appComponent.inject(this)
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

        (activity_list.adapter as InviteActivityAdapter).clearActivities()
        invitationService.getMyInvitations().enqueue(object: Callback<Invitations> {

            override fun onFailure(call: Call<Invitations>, t: Throwable) {
                Toast.makeText(context, "Failed to fetch invitations: ${t.message}", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<Invitations>,
                response: Response<Invitations>
            ) {
                if (response.body() != null) {
                    (activity_list.adapter as InviteActivityAdapter).addActivities(listOf(InviteActivity.TitleType("Invitations")))
                    (activity_list.adapter as InviteActivityAdapter).addActivities(
                            response.body()!!.invitations
                            .map {
                                InviteActivity.InviteType(
                                        it
                                )
                            })
                }
                activityService.getVisibleActivities().enqueue(object: Callback<Activities> {
                    override fun onFailure(call: Call<Activities>, t: Throwable) {
                        Toast.makeText(context, "Failed to fetch activities: ${t.message}", Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(
                            call: Call<Activities>,
                            response: Response<Activities>
                    ) {
                        if (response.body() != null) {
                            (activity_list.adapter as InviteActivityAdapter).addActivities(listOf(InviteActivity.TitleType("All activities")))
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
        })
    }

}