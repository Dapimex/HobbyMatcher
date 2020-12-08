package com.madness.hobbymatcher.loginmanager.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.madness.hobbymatcher.loginmanager.R
import com.madness.hobbymatcher.loginmanager.databinding.ActivityLoginBinding
import com.madness.hobbymatcher.loginmanager.misc.errorFieldIfEmpty
import com.madness.hobbymatcher.loginmanager.misc.trimField

class LoginActivity : AppCompatActivity() {

    private var _layout: ActivityLoginBinding? = null
    private val layout get() = _layout!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _layout = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(layout.root)
    }

    fun onLoginButtonClick(button: View) {
        button as Button
        with(layout) {
            trimField(editUsername)
            trimField(editPassword)
            errorFieldIfEmpty(editUsername, getString(R.string.err_edit_empty))
            errorFieldIfEmpty(editPassword, getString(R.string.err_edit_empty))
        }
    }

}