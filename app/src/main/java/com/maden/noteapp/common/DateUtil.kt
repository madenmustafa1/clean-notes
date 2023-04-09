package com.maden.noteapp.common

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    @SuppressLint("SimpleDateFormat")
    fun getCurrentDate(): String {
        val timeStampFormat = SimpleDateFormat("dd-MM-yyyy")
        return timeStampFormat.format(Date())
    }
}