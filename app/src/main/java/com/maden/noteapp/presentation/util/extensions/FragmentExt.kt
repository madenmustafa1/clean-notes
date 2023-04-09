package com.maden.noteapp.presentation.util.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar.Duration

fun Fragment.showToast(message: String, @Duration duration: Int = Toast.LENGTH_LONG)  {
    Toast.makeText(requireContext(), message, duration).show()
}