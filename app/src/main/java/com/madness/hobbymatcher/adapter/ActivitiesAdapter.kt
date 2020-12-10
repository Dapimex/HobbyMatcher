package com.madness.hobbymatcher.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.madness.hobbymatcher.R
import com.madness.hobbymatcher.networking.response.Activity

class ActivitiesAdapter()
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val activities: MutableList<Activity> = mutableListOf()

    class ActivityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.activityNameListItemTextView)
        val descriptionTextView: TextView = view.findViewById(R.id.activityDescriptionListItemTextView)
        val startTimeTextView: TextView = view.findViewById(R.id.activityStartTimeListItemTextView)
        val locationTextView: TextView = view.findViewById(R.id.activityStartTimeListItemTextView)

        fun bind(activity: Activity) {
            nameTextView.text = activity.name
            descriptionTextView.text = activity.description
            startTimeTextView.text = activity.startTime
            locationTextView.text = activity.location
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        return ActivityViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return activities.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        println(position)
        (holder as ActivityViewHolder).bind(activities[position])
    }

    fun addActivities(activities: List<Activity>) {
        this.activities.addAll(activities)
        println("Added this: " + this.activities.toString())
        notifyDataSetChanged()
    }

    fun clearActivities() {
        this.activities.clear()
        notifyDataSetChanged()
    }
}
