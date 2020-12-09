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

    private var _loginManager: LoginManager? = null
    private val loginManager get() = _loginManager!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _layout = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(layout.root)

        _loginManager = LoginManager(this)
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

        if (ensurePasswordsOkay(
                editPassword, editPasswordRepeat
            ) && editUsername.text.isNotEmpty()
        ) {
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

    private fun ensurePasswordsOkay(field1: EditText, field2: EditText): Boolean {
        if (field1.text.isEmpty() || field2.text.isEmpty()) {
            errorFieldIfEmpty(field1, getString(R.string.err_edit_empty))
            errorFieldIfEmpty(field2, getString(R.string.err_edit_empty))
            return false
        }

        val passwordsAreSame = field1.text.toString() == field2.text.toString()
        if (!passwordsAreSame) {
            field1.error = getString(R.string.err_passwords_not_same)
            field2.error = getString(R.string.err_passwords_not_same)
        }
        return passwordsAreSame
    }
}