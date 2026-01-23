package com.mtv.app.movie.common

import java.text.SimpleDateFormat
import java.util.*

fun String.formatDateAutoLegacy(outputPattern: String): String {
    val date = when {
        all { it.isDigit() } && length >= 13 ->
            Date(toLong())

        all { it.isDigit() } && length == 10 ->
            Date(toLong() * 1000)

        contains("T") ->
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                .parse(this)

        length == 10 ->
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .parse(this)

        else ->
            null
    } ?: return ""

    return SimpleDateFormat(outputPattern, Locale.getDefault()).format(date)
}
