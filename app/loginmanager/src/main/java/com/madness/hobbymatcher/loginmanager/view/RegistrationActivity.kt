package com.madness.hobbymatcher.loginmanager.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.madness.hobbymatcher.loginmanager.R
import com.madness.hobbymatcher.loginmanager.databinding.ActivityRegistrationBinding
import com.madness.hobbymatcher.loginmanager.misc.*
import com.madness.hobbymatcher.loginmanager.security.LoginManager
import com.madness.hobbymatcher.loginmanager.security.LoginResult

class RegistrationActivity : AppCompatActivity() {

    private var _layout: ActivityRegistrationBinding? = null
    private val layout get() = _layout!!

    private val loginManager = LoginManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _layout = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(layout.root)
    }

    fun onRegisterButtonClick(button: View) {
        button as Button
        val editUsername = layout.editUsername
        val editPassword = layout.editPassword
        val editPasswordRepeat = layout.editPasswordRepeat

        for (editText in arrayOf(editUsername, editPassword, editPasswordRepeat)) {
            trimField(editText)
            errorFieldIfEmpty(editText, getString(R.string.err_edit_empty))
        }

        if (ensurePasswordsSame(editPassword, editPasswordRepeat)) {
            setControlsBusy(button, layout.progressBar)
            val username = editUsername.text.toString()
            val password = editPassword.text.toString()

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
                unsetControlsBusy(button, layout.progressBar)
            })
        }

    }

    private fun ensurePasswordsSame(field1: EditText, field2: EditText): Boolean {
        val passwordsSame = field1.text == field2.text
        if (!passwordsSame) {
            field1.error = getString(R.string.err_passwords_not_same)
            field2.error = getString(R.string.err_passwords_not_same)
        }
        return passwordsSame
    }
}