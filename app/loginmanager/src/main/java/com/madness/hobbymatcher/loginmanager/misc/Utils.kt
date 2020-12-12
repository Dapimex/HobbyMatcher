package com.madness.hobbymatcher.loginmanager.misc

import android.text.Editable
import android.view.View
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar

fun trimField(edit: EditText) {
    edit.text = edit.text.trim() as Editable
}

fun errorFieldIfEmpty(edit: EditText, reason: String) {
    if (edit.text.isEmpty()) {
        edit.error = reason
    }
}

fun setViewsEnabled(enabled: Boolean, vararg views: View) {
    for (view in views) {
        view.isEnabled = enabled
    }
}

fun snackbarMessage(view: View, message: String) {
    val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    snackbar.animationMode = Snackbar.ANIMATION_MODE_SLIDE
    snackbar.show()
}