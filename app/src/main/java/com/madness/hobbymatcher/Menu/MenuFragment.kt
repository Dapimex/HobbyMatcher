package com.madness.hobbymatcher.Menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.madness.hobbymatcher.R
import com.madness.hobbymatcher.networking.response.Invitation
import com.madness.hobbymatcher.networking.response.Activity

sealed class InviteActivity {
    data class InviteType(val invite: Invitation): InviteActivity()
    data class ActivityType(val activity: Activity): InviteActivity()
}

class MenuFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

}