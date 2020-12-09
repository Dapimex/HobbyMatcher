package com.madness.hobbymatcher.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.madness.hobbymatcher.R
import com.madness.hobbymatcher.networking.response.Activity

class ActivitiesAdapter(private val activities: List<Activity>)
    : RecyclerView.Adapter<ActivitiesAdapter.ActivityViewHolder>() {

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

    private fun ViewGroup.inflate(layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        return ActivityViewHolder(parent.inflate(R.layout.activity_list_item))
    }

    override fun getItemCount(): Int {
        return activities.size
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        holder.bind(activities[position])
    }

}
