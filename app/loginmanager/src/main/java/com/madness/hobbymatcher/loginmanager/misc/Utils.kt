package com.madness.hobbymatcher.loginmanager.misc

import android.text.Editable
import android.widget.EditText

fun trimField(edit: EditText) {
    edit.text = edit.text.trim() as Editable
}

fun errorFieldIfEmpty(edit: EditText, reason: String) {
    if (edit.text.isEmpty()) {
        edit.error = reason
    }
}