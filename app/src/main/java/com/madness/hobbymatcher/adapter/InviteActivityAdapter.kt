package com.madness.hobbymatcher.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
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
    private val ITEM_VIEW_TYPE_TITLE = 2

    class TitleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView: TextView = view.findViewById(R.id.titleNameItemTextView)

        fun bind(text: String) {
            textView.text = text
        }
    }

    class ActivityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val frontSdf = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
        private val backSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

        private val nameTextView: TextView = view.findViewById(R.id.activityNameListItemTextView)
        private val metaTextView: TextView = view.findViewById(R.id.activityMetaListItemTextView)
        private val deleteButton: ImageButton = view.findViewById(R.id.activityListItemDeleteButton)

        fun bind(activity: Activity) {
            nameTextView.text = activity.name
            var startTime = ""
            if (activity.startTime != null) {
                startTime = frontSdf.format(backSdf.parse(activity.startTime!!)!!)
            }
            metaTextView.text = "$startTime | ${activity.location}"
            deleteButton.visibility = View.INVISIBLE
            deleteButton.isClickable = false
            itemView.setOnClickListener {
                itemView.findNavController().navigate(
                    R.id.action_menu_item_home_to_activityDetailFragment,
                    bundleOf("id" to activity.id)
                )
            }
        }
    }

    class InviteViewHolder(view: View, private val deleteFunction: (InviteActivity) -> Unit) : RecyclerView.ViewHolder(view) {

        @Inject
        lateinit var invitationService: InvitationService

        private val frontSdf = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
        private val backSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

        private val activityNameTextView: TextView = view.findViewById(R.id.inviteActivityNameListItemTextView)
        private val activityMetaTextView: TextView = view.findViewById(R.id.inviteActivityMetaListItemTextView)
        private val senderTextView: TextView = view.findViewById(R.id.inviteSenderListItemTextView)
        private val acceptButton: Button = view.findViewById(R.id.inviteListItemAcceptButton)
        private val declineButton: Button = view.findViewById(R.id.inviteListItemDeclineButton)

        fun bind(invite: Invitation) {
            (HobbyMatcherApplication.APPLICATION as HobbyMatcherApplication).appComponent.inject(this)
            activityNameTextView.text = invite.activity?.name
            var startTime = ""
            if (invite.activity?.startTime != null) {
                startTime = frontSdf.format(backSdf.parse(invite.activity?.startTime!!)!!)
            }
            activityMetaTextView.text = "$startTime | ${invite.activity?.location}"
            val createTime = frontSdf.format(backSdf.parse(invite.creationTime!!)!!)
            senderTextView.text = "${createTime}: ${invite.senderUsername} invited you"

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
            itemView.setOnClickListener {
                itemView.findNavController().navigate(
                    R.id.action_menu_item_home_to_activityDetailFragment,
                    bundleOf("id" to invite.activity?.id)
                )
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
            is InviteActivity.TitleType -> ITEM_VIEW_TYPE_TITLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        ITEM_VIEW_TYPE_INVITE -> InviteViewHolder(parent.inflate(
                R.layout.invite_list_item)) { activity: InviteActivity -> deleteActivity(activity) }
        ITEM_VIEW_TYPE_ACTIVITY -> ActivityViewHolder(parent.inflate(R.layout.activity_list_item))
        ITEM_VIEW_TYPE_TITLE -> TitleViewHolder(parent.inflate(R.layout.title_list_item))
        else -> ActivityViewHolder(parent.inflate(R.layout.activity_list_item))
    }


    override fun getItemCount(): Int {
        return activities.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = activities[holder.adapterPosition]) {
            is InviteActivity.InviteType -> (holder as InviteViewHolder).bind(item.invite)
            is InviteActivity.ActivityType -> (holder as ActivityViewHolder).bind(item.activity)
            is InviteActivity.TitleType -> (holder as TitleViewHolder).bind(item.text)
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

