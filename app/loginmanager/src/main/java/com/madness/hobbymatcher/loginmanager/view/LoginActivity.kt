package com.madness.hobbymatcher.loginmanager.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.madness.hobbymatcher.loginmanager.R
import com.madness.hobbymatcher.loginmanager.databinding.ActivityLoginBinding
import com.madness.hobbymatcher.loginmanager.misc.errorFieldIfEmpty
import com.madness.hobbymatcher.loginmanager.misc.setViewsEnabled
import com.madness.hobbymatcher.loginmanager.misc.snackbarMessage
import com.madness.hobbymatcher.loginmanager.misc.trimField
import com.madness.hobbymatcher.loginmanager.security.LoginManager
import com.madness.hobbymatcher.loginmanager.security.LoginResult
import dagger.android.AndroidInjection
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    @Inject
    lateinit var loginManager: LoginManager

    private var _layout: ActivityLoginBinding? = null
    private val layout get() = _layout!!

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        _layout = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(layout.root)
    }

    fun onLoginButtonClick(button: View) {
        button as Button
        val editUsername = layout.editUsername
        val editPassword = layout.editPassword

        trimField(editUsername)
        trimField(editPassword)
        errorFieldIfEmpty(editUsername, getString(R.string.err_edit_empty))
        errorFieldIfEmpty(editPassword, getString(R.string.err_edit_empty))

        val username = editUsername.text
        val password = editPassword.text
        if (username.isNotEmpty() && password.isNotEmpty()) {
            setViewsEnabled(false, button, editUsername, editPassword)
            layout.progressBar.visibility = View.VISIBLE

            val signInResult = loginManager.startSignIn(username.toString(), password.toString())
            signInResult.observe(this, { loginResult ->
                when (loginResult) {
                    LoginResult.SUCCESS -> finish()
                    LoginResult.INVALID -> snackbarMessage(
                        layout.root,
                        getString(R.string.msg_invalid_cred)
                    )
                    LoginResult.NO_INTERNET -> snackbarMessage(
                        layout.root,
                        getString(R.string.msg_no_internet)
                    )
                    else -> {
                    }
                }

                setViewsEnabled(true, button, editUsername, editPassword)
                layout.progressBar.visibility = View.INVISIBLE
            })
        }
    }

}