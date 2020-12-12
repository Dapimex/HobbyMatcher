package com.madness.hobbymatcher.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.madness.hobbymatcher.R
import kotlinx.android.synthetic.main.participant_list_item.view.*

class ParticipantsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val users: MutableList<String> = mutableListOf()

    class ParticipantsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(username: String) {
            view.participantUsernameTextView.text = username
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ParticipantsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.participant_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ParticipantsViewHolder).bind(users[position])
    }

    fun addUsers(users: List<String>) {
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    fun clearUsers() {
        users.clear()
        notifyDataSetChanged()
    }
}