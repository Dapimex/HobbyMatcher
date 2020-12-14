package com.madness.hobbymatcher.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.madness.hobbymatcher.HobbyMatcherApplication
import com.madness.hobbymatcher.R
import com.madness.hobbymatcher.networking.InvitationService
import com.madness.hobbymatcher.networking.UserService
import com.madness.hobbymatcher.networking.interceptors.CredentialsStore
import com.madness.hobbymatcher.networking.response.Activity
import com.madness.hobbymatcher.networking.response.Invitation
import com.madness.hobbymatcher.networking.response.User
import com.madness.hobbymatcher.networking.response.UsersResponse
import kotlinx.android.synthetic.main.fragment_invitations.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class InvitationsFragment : Fragment() {

    @Inject
    lateinit var userService: UserService

    @Inject
    lateinit var invitationService: InvitationService

    @Inject
    lateinit var credentialsStore: CredentialsStore

    lateinit var activity: Activity
    private val args: InvitationsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = args.activity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (HobbyMatcherApplication.APPLICATION as HobbyMatcherApplication).appComponent.inject(this)
        return inflater.inflate(R.layout.fragment_invitations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activityNameTextView.text = activity.name

        val options = mutableListOf<String>()
        val adapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, options)
        usernameAutoCompleteTextView.setAdapter(adapter)

        var targetUserId: Int?

        usernameAutoCompleteTextView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                userService.getAllUsersNotInActivityWithUsernameLike(activity.id!!, s.toString())
                    .enqueue(object : Callback<UsersResponse> {
                        override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                            Toast.makeText(context, "Failed to fetch options", Toast.LENGTH_SHORT)
                                .show()
                        }

                        override fun onResponse(
                            call: Call<UsersResponse>,
                            response: Response<UsersResponse>
                        ) {
                            if (response.isSuccessful) {
                                adapter.clear()
                                adapter.addAll(response.body()!!.users.map { it.username })
                            }
                        }
                    })
            }
        })

        sendInvitationButton.setOnClickListener {
            val enteredUsername = usernameAutoCompleteTextView.text.toString()

            if (credentialsStore.username.equals(enteredUsername)) {
                Toast.makeText(
                    context,
                    getString(R.string.err_connot_invite_yourself),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                userService.getUserByUsername(enteredUsername).enqueue(object : Callback<User> {
                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Toast.makeText(
                            context,
                            "Failed to fetch user with username $enteredUsername",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {
                            targetUserId = response.body()!!.id!!

                            invitationService.createInvitation(
                                Invitation(
                                    activityId = activity.id,
                                    targetUserId = targetUserId
                                )
                            ).enqueue(object : Callback<ResponseBody> {
                                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                    Toast.makeText(
                                        context,
                                        "Failed to send invitation to user $targetUserId",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                override fun onResponse(
                                    call: Call<ResponseBody>,
                                    response: Response<ResponseBody>
                                ) {
                                    if (response.isSuccessful) {
                                        Toast.makeText(
                                            context,
                                            getString(R.string.msg_invitation_sent),
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                        usernameAutoCompleteTextView.text = null
                                    }
                                }
                            })
                        } else if (response.code() == 400) {
                            targetUserId = null
                            Toast.makeText(
                                context,
                                "User with username $enteredUsername not found",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
            }
        }
    }
}