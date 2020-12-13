package com.madness.hobbymatcher.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.madness.hobbymatcher.R
import com.madness.hobbymatcher.networking.response.Activity

class ActivitiesAdapter(
    private val deleteFunction: ((Int) -> Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val myActivities: MutableList<Activity> = mutableListOf()
    private val joinedActivities: MutableList<Activity> = mutableListOf()

    private val ITEM_VIEW_TYPE_ACTIVITY = 0
    private val ITEM_VIEW_TYPE_TITLE = 1

    class TitleViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        private val titleTextView: TextView = view.findViewById(R.id.titleNameItemTextView)

        fun bind(text: String) {
            titleTextView.text = text
        }
    }

    class ActivityViewHolder(val view: View, private val deleteFunction: (Int) -> Unit) :
        RecyclerView.ViewHolder(view) {
        private val nameTextView: TextView = view.findViewById(R.id.activityNameListItemTextView)
        private val startTimeTextView: TextView =
            view.findViewById(R.id.activityStartTimeListItemTextView)
        private val locationTextView: TextView =
            view.findViewById(R.id.activityStartTimeListItemTextView)
        private val deleteActivityButton: ImageButton =
            view.findViewById(R.id.activityListItemDeleteButton)
        private val holder: ConstraintLayout = view.findViewById(R.id.listItemHolder)

        fun bind(activity: Activity, isMyActivity: Boolean) {
            if (isMyActivity) {

                holder.backgroundTintList = ContextCompat.getColorStateList(view.context, R.color.lightGreenColor)
                deleteActivityButton.setOnClickListener {
                    activity.id?.let { deleteFunction.invoke(it) }
                }
                deleteActivityButton.visibility = VISIBLE
                deleteActivityButton.isClickable = true
            } else {
                deleteActivityButton.visibility = INVISIBLE
                deleteActivityButton.isClickable = false
            }
            nameTextView.text = activity.name
            startTimeTextView.text = activity.startTime
            locationTextView.text = activity.location


            view.setOnClickListener {
                view.findNavController().navigate(
                    R.id.action_menu_item_profile_to_activityDetailFragment,
                    bundleOf("id" to activity.id)
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ITEM_VIEW_TYPE_ACTIVITY) {
            return ActivityViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.activity_list_item, parent, false),
                deleteFunction
            )
        } else {
            return TitleViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.title_list_item, parent, false)
            )
        }
    }

    override fun getItemCount(): Int {
        return myActivities.size + joinedActivities.size + 2
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0 || position == myActivities.size + 1)
            return ITEM_VIEW_TYPE_TITLE
        return ITEM_VIEW_TYPE_ACTIVITY
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TitleViewHolder) {
            if (position == 0)
                holder.bind("Created activities")
            else
                holder.bind("Joined activities")
        } else {
            if (position > myActivities.size + 1) {
                (holder as ActivityViewHolder).bind(
                    joinedActivities[position - 2 - myActivities.size],
                    false
                )
            } else {
                (holder as ActivityViewHolder).bind(myActivities[position - 1], true)
            }
        }
    }

    fun addMyActivities(activities: List<Activity>) {
        this.myActivities.addAll(activities)
        notifyDataSetChanged()
    }

    fun addJoinedActivities(activities: List<Activity>) {
        this.joinedActivities.addAll(activities)
        notifyDataSetChanged()
    }

    fun clearActivities() {
        this.myActivities.clear()
        this.joinedActivities.clear()
        notifyDataSetChanged()
    }
}
