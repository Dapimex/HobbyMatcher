package com.madness.hobbymatcher.loginmanager.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.madness.hobbymatcher.loginmanager.R
import com.madness.hobbymatcher.loginmanager.databinding.ActivityLoginBinding
import com.madness.hobbymatcher.loginmanager.misc.*
import com.madness.hobbymatcher.loginmanager.security.LoginManager
import com.madness.hobbymatcher.loginmanager.security.LoginResult

class LoginActivity : AppCompatActivity() {

    private var _layout: ActivityLoginBinding? = null
    private val layout get() = _layout!!

    private var _loginManager: LoginManager? = null
    private val loginManager get() = _loginManager!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _layout = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(layout.root)

        _loginManager = LoginManager(this)
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
            setControlsBusy(button, layout.progressBar)

            val signInResult = loginManager.startSignIn(username.toString(), password.toString())
            signInResult.observe(this, Observer { loginResult ->
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
                unsetControlsBusy(button, layout.progressBar)
            })
        }
    }

}