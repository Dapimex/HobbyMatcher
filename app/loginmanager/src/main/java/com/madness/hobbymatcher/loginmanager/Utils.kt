package com.madness.hobbymatcher.loginmanager

import android.text.Editable
import android.widget.EditText

class Utils {
    companion object {
        fun trimField(edit: EditText) {
            edit.text = edit.text.trim() as Editable
        }

        fun errorFieldIfEmpty(edit: EditText, reason: String) {
            if (edit.text.isEmpty()) {
                edit.error = reason
            }
        }
    }
}