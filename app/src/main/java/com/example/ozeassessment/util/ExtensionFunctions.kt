package com.example.ozeassessment.util

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar


fun View.showSnackBar( message:String) {
     Snackbar.make(this, message, Snackbar.LENGTH_SHORT).setAction("Okay") {}
          .show()
}