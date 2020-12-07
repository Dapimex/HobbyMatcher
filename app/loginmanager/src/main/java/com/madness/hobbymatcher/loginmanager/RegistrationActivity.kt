package com.madness.hobbymatcher.loginmanager

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.madness.hobbymatcher.loginmanager.Utils.Companion.errorFieldIfEmpty
import com.madness.hobbymatcher.loginmanager.Utils.Companion.trimField
import com.madness.hobbymatcher.loginmanager.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {

    private var _layout: ActivityRegistrationBinding? = null
    private val layout get() = _layout!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _layout = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(layout.root)
    }

    fun onRegisterButtonClick(button: View) {
        button as Button
        with(layout) {
            for (editText in arrayOf(editUsername, editPassword, editPasswordRepeat)) {
                trimField(editText)
                errorFieldIfEmpty(editText, getString(R.string.err_edit_empty))
            }
            ensurePasswordsSame(editPassword, editPasswordRepeat)
        }
    }

    private fun ensurePasswordsSame(field1: EditText, field2: EditText) {
        if (field1.text != field2.text) {
            field1.error = getString(R.string.err_passwords_not_same)
            field2.error = getString(R.string.err_passwords_not_same)
        }
    }
}