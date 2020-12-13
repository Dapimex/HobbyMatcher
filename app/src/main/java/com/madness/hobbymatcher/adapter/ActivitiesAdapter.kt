package com.madness.hobbymatcher.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
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

    class ActivityViewHolder(val view: View, private val deleteFunction: (Int) -> Unit) :
        RecyclerView.ViewHolder(view) {
        private val nameTextView: TextView = view.findViewById(R.id.activityNameListItemTextView)
        private val descriptionTextView: TextView =
            view.findViewById(R.id.activityDescriptionListItemTextView)
        private val startTimeTextView: TextView =
            view.findViewById(R.id.activityStartTimeListItemTextView)
        private val locationTextView: TextView =
            view.findViewById(R.id.activityStartTimeListItemTextView)
        private val deleteActivityButton: ImageButton =
            view.findViewById(R.id.activityListItemDeleteButton)
        private val holder: ConstraintLayout = view.findViewById(R.id.listItemHolder)

        fun bind(activity: Activity, isMyActivity: Boolean) {
            if (isMyActivity) {
                holder.setBackgroundResource(R.drawable.custom_border)
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
            descriptionTextView.text = activity.description
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        return ActivityViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_list_item, parent, false),
            deleteFunction
        )
    }

    override fun getItemCount(): Int {
        return myActivities.size + joinedActivities.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position >= myActivities.size) {
            (holder as ActivityViewHolder).bind(
                joinedActivities[position - myActivities.size],
                false
            )
        } else {
            (holder as ActivityViewHolder).bind(myActivities[position], true)
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
