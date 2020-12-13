package com.madness.hobbymatcher.loginmanager.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.madness.hobbymatcher.loginmanager.R
import com.madness.hobbymatcher.loginmanager.databinding.ActivityWelcomeBinding
import com.madness.hobbymatcher.loginmanager.misc.errorFieldIfEmpty
import com.madness.hobbymatcher.loginmanager.misc.setViewsEnabled
import com.madness.hobbymatcher.loginmanager.misc.snackbarMessage
import com.madness.hobbymatcher.loginmanager.misc.trimField
import com.madness.hobbymatcher.loginmanager.security.LoginManager
import com.madness.hobbymatcher.loginmanager.security.LoginResult
import dagger.android.AndroidInjection
import javax.inject.Inject

class WelcomeActivity : AppCompatActivity() {

    @Inject
    lateinit var loginManager: LoginManager

    private var _layout: ActivityWelcomeBinding? = null
    private val layout get() = _layout!!

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this) // TODO Injector factory
        super.onCreate(savedInstanceState)
        _layout = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(layout.root)
    }

    fun onLetsGoButtonClick(button: View) {
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
                    LoginResult.INVALID -> {
                        layout.editPasswordRepeat.visibility = View.VISIBLE
                        setViewsEnabled(true, layout.editPasswordRepeat)
                        button.setOnClickListener(this::onRegisterButtonClick)
                    }
                    LoginResult.NO_INTERNET -> {
                        snackbarMessage(
                            layout.root, getString(R.string.msg_no_internet)
                        )
                        setViewsEnabled(true, button, editUsername, editPassword)
                    }
                    else -> {
                    }
                }

                layout.progressBar.visibility = View.INVISIBLE
                setViewsEnabled(true, button)
            })
        }
    }

    fun onRegisterButtonClick(button: View) {
        button as Button
        val editPasswordRepeat = layout.editPasswordRepeat

        errorFieldIfEmpty(editPasswordRepeat, getString(R.string.err_edit_empty))
        if (editPasswordRepeat.text.isNotEmpty()) {
            if (editPasswordRepeat.text.toString() == layout.editPassword.text.toString()) {
                setViewsEnabled(false, button, editPasswordRepeat)
                layout.progressBar.visibility = View.VISIBLE

                val username = layout.editUsername.text.toString()
                val password = layout.editPassword.text.toString()
                val signUpResult = loginManager.startSignUp(username, password)
                signUpResult.observe(this, { loginResult ->
                    when (loginResult) {
                        LoginResult.SUCCESS -> finish()
                        LoginResult.INVALID -> snackbarMessage(
                            layout.root,
                            getString(R.string.msg_username_exists)
                        )
                        LoginResult.NO_INTERNET -> snackbarMessage(
                            layout.root,
                            getString(R.string.msg_no_internet)
                        )
                        else -> {
                        }
                    }

                    layout.progressBar.visibility = View.INVISIBLE
                    setViewsEnabled(true, button, editPasswordRepeat)
                })

            } else {
                editPasswordRepeat.error = getString(R.string.err_passwords_dont_match)
            }
        }
    }

}