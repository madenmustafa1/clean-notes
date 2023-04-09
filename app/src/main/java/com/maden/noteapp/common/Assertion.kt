package com.maden.noteapp.common

fun Int.checkUuid() {
    check(this > 0) { "Invalid UUID!" }
}

fun String.checkData() {
    check(this.isNotEmpty()) { "Data cannot be not empty!" }
}