package com.example.dailynews.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateTimeUtil {

    fun formatDate(dateString: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy 'at' h:mm a", Locale.getDefault())

        val instant = Instant.parse(dateString)
        val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())

        return outputFormatter.format(localDateTime)
    }
}