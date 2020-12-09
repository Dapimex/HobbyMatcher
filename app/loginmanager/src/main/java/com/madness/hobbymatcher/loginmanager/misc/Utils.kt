package com.madness.hobbymatcher.loginmanager.misc

import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import com.google.android.material.snackbar.Snackbar

fun trimField(edit: EditText) {
    edit.text = edit.text.trim() as Editable
}

fun errorFieldIfEmpty(edit: EditText, reason: String) {
    if (edit.text.isEmpty()) {
        edit.error = reason
    }
}

fun setControlsBusy(button: Button, progressBar: ProgressBar) {
    button.isEnabled = false
    progressBar.visibility = View.VISIBLE
}

fun unsetControlsBusy(button: Button, progressBar: ProgressBar) {
    button.isEnabled = true
    progressBar.visibility = View.INVISIBLE
}

fun snackbarMessage(view: View, message: String) {
    val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    snackbar.animationMode = Snackbar.ANIMATION_MODE_SLIDE
    snackbar.show()
}