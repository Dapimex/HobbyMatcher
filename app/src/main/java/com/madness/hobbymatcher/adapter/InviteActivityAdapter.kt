package com.madness.hobbymatcher.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.madness.hobbymatcher.Home.InviteActivity
import com.madness.hobbymatcher.R
import com.madness.hobbymatcher.networking.response.Activity
import com.madness.hobbymatcher.networking.response.Invitation

class InviteActivityAdapter
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val activities: MutableList<InviteActivity> = mutableListOf()

    private val ITEM_VIEW_TYPE_ACTIVITY = 0
    private val ITEM_VIEW_TYPE_INVITE = 1

    class ActivityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameTextView: TextView = view.findViewById(R.id.activityNameListItemTextView)
        private val descriptionTextView: TextView = view.findViewById(R.id.activityDescriptionListItemTextView)
        private val startTimeTextView: TextView = view.findViewById(R.id.activityStartTimeListItemTextView)
        private val locationTextView: TextView = view.findViewById(R.id.activityStartTimeListItemTextView)

        fun bind(activity: Activity) {
            nameTextView.text = activity.name
            descriptionTextView.text = activity.description
            startTimeTextView.text = activity.startTime
            locationTextView.text = activity.location
        }
    }

    class InviteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val activityNameTextView: TextView = view.findViewById(R.id.inviteActivityNameListItemTextView)
        val activityDescriptionTextView: TextView = view.findViewById(R.id.inviteActivityDescriptionListItemTextView)
        val activityStartTimeTextView: TextView = view.findViewById(R.id.inviteActivityStartTimeListItemTextView)
        val activityLocationTextView: TextView = view.findViewById(R.id.inviteActivityLocationListItemTextView)
        val senderTextView: TextView = view.findViewById(R.id.inviteSenderListItemTextView)
        val timeTextView: TextView = view.findViewById(R.id.inviteTimeListItemTextView)

        fun bind(invite: Invitation) {
            activityNameTextView.text = invite.activity?.name
            activityDescriptionTextView.text = invite.activity?.description
            activityStartTimeTextView.text = invite.activity?.startTime
            activityLocationTextView.text = invite.activity?.location
            senderTextView.text = invite.senderUsername
            timeTextView.text = invite.creationTime
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
        ITEM_VIEW_TYPE_INVITE -> InviteViewHolder(parent.inflate(R.layout.invite_list_item))
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

    fun clearActivities() {
        this.activities.clear()
        notifyDataSetChanged()
    }
}

