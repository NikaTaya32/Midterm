package com.example.homework3.presentation.extension

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(title: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, title, duration).show()
}