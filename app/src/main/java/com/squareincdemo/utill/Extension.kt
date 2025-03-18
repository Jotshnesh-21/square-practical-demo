package com.squareincdemo.utill

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import java.io.Serializable

fun View.showSnackBar(message: String) {
    val snackBar = Snackbar.make(
        this, message,
        Snackbar.LENGTH_LONG
    )/*.setAction("Ok") {
    }*/
    snackBar.setActionTextColor(Color.BLACK)
    val snackBarView = snackBar.view
    snackBarView.setBackgroundColor(Color.RED)
    val textView: TextView =
        snackBarView.findViewById(com.google.android.material.R.id.snackbar_text)
    textView.setTextColor(Color.WHITE)
    textView.gravity = Gravity.CENTER_HORIZONTAL
    snackBar.show()
}

@Suppress("DEPRECATION")
inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(
        key, T::class.java
    ) else -> getSerializableExtra(key) as? T
}