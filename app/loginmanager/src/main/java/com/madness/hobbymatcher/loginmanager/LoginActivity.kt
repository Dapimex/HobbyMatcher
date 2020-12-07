package com.madness.hobbymatcher.loginmanager

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.madness.hobbymatcher.loginmanager.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private var _layout: ActivityLoginBinding? = null
    private val layout get() = _layout!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _layout = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(layout.root)
    }

    @Suppress("UNUSED_PARAMETER")
    fun onLoginButtonClick(view: View) {
        with(layout) {
            trimField(editUsername)
            trimField(editPassword)
            errorFieldIfEmpty(editUsername)
            errorFieldIfEmpty(editPassword)
        }
    }

    private fun trimField(edit: EditText) {
        edit.text = edit.text.trim() as Editable
    }

    private fun errorFieldIfEmpty(edit: EditText) {
        if (edit.text.isEmpty()) {
            edit.error = getString(R.string.err_edit_empty)
        }
    }

}