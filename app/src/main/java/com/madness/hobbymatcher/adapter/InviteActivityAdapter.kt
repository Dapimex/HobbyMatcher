package com.madness.hobbymatcher.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.madness.hobbymatcher.HobbyMatcherApplication
import com.madness.hobbymatcher.fragment.InviteActivity
import com.madness.hobbymatcher.R
import com.madness.hobbymatcher.networking.InvitationService
import com.madness.hobbymatcher.networking.response.Activity
import com.madness.hobbymatcher.networking.response.Invitation
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class InviteActivityAdapter
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val activities: MutableList<InviteActivity> = mutableListOf()

    private val ITEM_VIEW_TYPE_ACTIVITY = 0
    private val ITEM_VIEW_TYPE_INVITE = 1

    class ActivityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val frontSdf = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
        private val backSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

        private val nameTextView: TextView = view.findViewById(R.id.activityNameListItemTextView)
        private val descriptionTextView: TextView = view.findViewById(R.id.activityDescriptionListItemTextView)
        private val startTimeTextView: TextView = view.findViewById(R.id.activityStartTimeListItemTextView)
        private val locationTextView: TextView = view.findViewById(R.id.activityStartTimeListItemTextView)
        private val deleteButton: ImageButton = view.findViewById(R.id.activityListItemDeleteButton)

        fun bind(activity: Activity) {
            nameTextView.text = activity.name
            descriptionTextView.text = activity.description
            if (activity.startTime != null) {
                startTimeTextView.text = frontSdf.format(backSdf.parse(activity.startTime!!)!!)
            }
            locationTextView.text = activity.location
            deleteButton.visibility = View.INVISIBLE
            deleteButton.isClickable = false
        }
    }

    class InviteViewHolder(view: View, private val deleteFunction: (InviteActivity) -> Unit) : RecyclerView.ViewHolder(view) {

        @Inject
        lateinit var invitationService: InvitationService

        private val frontSdf = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
        private val backSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

        private val activityNameTextView: TextView = view.findViewById(R.id.inviteActivityNameListItemTextView)
        private val activityDescriptionTextView: TextView = view.findViewById(R.id.inviteActivityDescriptionListItemTextView)
        private val activityStartTimeTextView: TextView = view.findViewById(R.id.inviteActivityStartTimeListItemTextView)
        private val activityLocationTextView: TextView = view.findViewById(R.id.inviteActivityLocationListItemTextView)
        private val senderTextView: TextView = view.findViewById(R.id.inviteSenderListItemTextView)
        private val timeTextView: TextView = view.findViewById(R.id.inviteTimeListItemTextView)
        private val acceptButton: ImageButton = view.findViewById(R.id.inviteListItemAcceptButton)
        private val declineButton: ImageButton = view.findViewById(R.id.inviteListItemDeclineButton)

        fun bind(invite: Invitation) {
            (HobbyMatcherApplication.APPLICATION as HobbyMatcherApplication).appComponent.inject(this)
            activityNameTextView.text = invite.activity?.name
            activityDescriptionTextView.text = invite.activity?.description
            if (invite.activity?.startTime != null) {
                activityStartTimeTextView.text = frontSdf.format(backSdf.parse(invite.activity?.startTime!!)!!)
            }
            activityLocationTextView.text = invite.activity?.location
            senderTextView.text = invite.senderUsername
            timeTextView.text = frontSdf.format(backSdf.parse(invite.creationTime!!)!!)

            acceptButton.setOnClickListener {
                invitationService.acceptInvitation(invite.id!!).enqueue(object: Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(itemView.context, "Failed to accept invitation: ${t.message}", Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        Toast.makeText(itemView.context, "Invitation accepted", Toast.LENGTH_LONG).show()
                    }

                })
                deleteFunction.invoke(InviteActivity.InviteType(invite))
            }

            declineButton.setOnClickListener {
                invitationService.declineInvitation(invite.id!!).enqueue(object: Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(itemView.context, "Failed to decline invitation: ${t.message}", Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        Toast.makeText(itemView.context, "Invitation declined", Toast.LENGTH_LONG).show()
                    }

                })
                deleteFunction.invoke(InviteActivity.InviteType(invite))
            }
        }
    }

    private fun ViewGroup.inflate(layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    override fun getItemViewType(position: Int): Int {
        return when(activities[position]) {
            is InviteActivity.InviteType -> ITEM_VIEW_TYPE_INVITE
            is InviteActivity.ActivityType -> ITEM_VIEW_TYPE_ACTIVITY
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        ITEM_VIEW_TYPE_INVITE -> InviteViewHolder(parent.inflate(
                R.layout.invite_list_item)) { activity: InviteActivity -> deleteActivity(activity) }
        ITEM_VIEW_TYPE_ACTIVITY -> ActivityViewHolder(parent.inflate(R.layout.activity_list_item))
        else -> ActivityViewHolder(parent.inflate(R.layout.activity_list_item))
    }


    override fun getItemCount(): Int {
        return activities.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = activities[holder.adapterPosition]) {
            is InviteActivity.InviteType -> (holder as InviteViewHolder).bind(item.invite)
            is InviteActivity.ActivityType -> (holder as ActivityViewHolder).bind(item.activity)
        }
    }

    fun addActivities(activities: List<InviteActivity>) {
        this.activities.addAll(activities)
        notifyDataSetChanged()
    }

    fun deleteActivity(activity: InviteActivity) {
        this.activities.remove(activity)
        notifyDataSetChanged()
    }

    fun clearActivities() {
        this.activities.clear()
        notifyDataSetChanged()
    }
}

