package com.madness.hobbymatcher

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.madness.hobbymatcher.loginmanager.view.WelcomeActivity
import com.madness.hobbymatcher.networking.UserService
import com.madness.hobbymatcher.networking.response.WhoAmI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var userService: UserService

    override fun onCreate(savedInstanceState: Bundle?) {
        (HobbyMatcherApplication.APPLICATION as HobbyMatcherApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        val homeIntent = Intent(this, HomeActivity::class.java)
        homeIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        val welcomeIntent = Intent(this, WelcomeActivity::class.java)

        userService.whoAmI().enqueue(object : Callback<WhoAmI> {
            override fun onResponse(call: Call<WhoAmI>, response: Response<WhoAmI>) {
                if (response.isSuccessful) {
                    startActivity(homeIntent)
                } else {
                    startActivity(welcomeIntent)
                }
            }

            override fun onFailure(call: Call<WhoAmI>, t: Throwable) {
                showNoInternet()
            }

        })
    }

    private fun showNoInternet() {
        val exitDialog = AlertDialog.Builder(this).apply {

            setTitle(R.string.text_no_internet)

            setMessage(R.string.text_cannot_operate_offline)

            setPositiveButton("Exit") { dialog, _ ->
                dialog.dismiss()
            }

            setOnDismissListener { _ -> finish() }

        }.create()

        exitDialog.show()
    }
}