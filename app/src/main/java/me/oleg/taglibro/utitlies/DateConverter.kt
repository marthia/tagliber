package me.oleg.taglibro.utitlies

import java.text.SimpleDateFormat
import java.util.*

fun getCurrentDateTime(): String {
    val sdf = SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.US)
    return sdf.format(Date()) ?: ""
}