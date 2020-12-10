package com.madness.hobbymatcher.loginmanager.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.madness.hobbymatcher.loginmanager.R
import com.madness.hobbymatcher.loginmanager.databinding.ActivityRegistrationBinding
import com.madness.hobbymatcher.loginmanager.misc.*
import com.madness.hobbymatcher.loginmanager.security.LoginManager
import com.madness.hobbymatcher.loginmanager.security.LoginResult
import dagger.android.AndroidInjection
import javax.inject.Inject

class RegistrationActivity : AppCompatActivity() {
    @Inject
    lateinit var loginManager: LoginManager

    private var _layout: ActivityRegistrationBinding? = null
    private val layout get() = _layout!!

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this);
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

        if (ensurePasswordsOkay(
                editPassword, editPasswordRepeat
            ) && editUsername.text.isNotEmpty()
        ) {
            setControlsBusy(button, layout.progressBar)
            val username = editUsername.text.toString()
            val password = editPassword.text.toString()

            val signUpResult = loginManager.startSignUp(username, password)
            signUpResult.observe(this, Observer { loginResult ->
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

    private fun ensurePasswordsOkay(
        passwordField: EditText,
        repeatPasswordField: EditText
    ): Boolean {
        if (passwordField.text.isEmpty() || repeatPasswordField.text.isEmpty()) {
            errorFieldIfEmpty(passwordField, getString(R.string.err_edit_empty))
            errorFieldIfEmpty(repeatPasswordField, getString(R.string.err_edit_empty))
            return false
        }

        val passwordsAreSame = passwordField.text.toString() == repeatPasswordField.text.toString()
        if (!passwordsAreSame) {
            passwordField.error = getString(R.string.err_passwords_not_same)
            repeatPasswordField.error = getString(R.string.err_passwords_not_same)
        }
        return passwordsAreSame
    }
}