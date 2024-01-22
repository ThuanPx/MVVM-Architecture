package com.thuanpx.view_mvvm_architecture.utils.extension.context

import android.annotation.SuppressLint
import android.content.Context
import android.text.format.DateUtils
import com.thuanpx.view_mvvm_architecture.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.TimeZone
import kotlin.math.abs

private const val SECOND_MILLIS = 1000
private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
private const val DAY_MILLIS = 24 * HOUR_MILLIS

@Throws(ParseException::class)
fun getLocalTime(timestamp: String?, simpleDateFormat: String?): Date? {
    val formatter = SimpleDateFormat(simpleDateFormat).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    return timestamp?.let { formatter.parse(it) }
}

@Throws(ParseException::class)
fun timestampToMilli(timestamp: String?, simpleDateFormat: String?): Long {
    val dateUtc = getLocalTime(timestamp, simpleDateFormat)
    val dateFormatter =
        SimpleDateFormat(simpleDateFormat).apply { timeZone = TimeZone.getDefault() }
    val localTimeString = dateUtc?.let { dateFormatter.format(it) }

    val date = localTimeString?.let { SimpleDateFormat(simpleDateFormat).parse(it) }
    return date!!.time
}

@SuppressLint("NewApi")
fun milliToStringTime(milli: Long): String {
    val instant = Instant.ofEpochMilli(milli)
    val date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    return formatter.format(date)
}

fun Context.getTimeAgo(
    timeString: String? = "",
    pattern: String = "yyyy-MM-dd'T'HH:mm:ss"
): String {
    if (timeString.isNullOrEmpty() ) {
        return  resources.getString(R.string.just_now)
    }
    try {
        var time = timestampToMilli(timeString, pattern)
        if (time < 1000000000000L) {
            time *= 1000
        }
        val diff: Long = abs(System.currentTimeMillis() - time)
        return if (diff < MINUTE_MILLIS) {
            resources.getString(R.string.just_now)
        } else if (diff < 2 * MINUTE_MILLIS) {
            resources.getString(R.string.min_ago)
        } else if (diff < 50 * MINUTE_MILLIS) {
            val minutes = diff / MINUTE_MILLIS
            "$minutes " + resources.getString(R.string.mins_ago)
        } else if (diff < 90 * MINUTE_MILLIS) {
            resources.getString(R.string.hour_ago)
        } else if (diff < 24 * HOUR_MILLIS) {
            val hours = (diff / HOUR_MILLIS).toString()
            "$hours " + resources.getString(R.string.hours_ago)
        } else if (diff < 7 * DAY_MILLIS) {
            if ((diff / DAY_MILLIS) == 1L) {
                resources.getString(R.string.day_ago)
            } else {
                val day = diff / DAY_MILLIS
                "$day " + resources.getString(R.string.days_ago)
            }
        } else if (diff < 4 * DateUtils.WEEK_IN_MILLIS) {
            if (diff / DateUtils.WEEK_IN_MILLIS == 1L) {
                resources.getString(R.string.week_ago)
            } else {
                val week = diff / DateUtils.WEEK_IN_MILLIS
                "$week " + resources.getString(R.string.weeks_ago)
            }
        } else {
            resources.getString(R.string.more_than_months_ago)
        }
    } catch (e: Exception) {
        return resources.getString(R.string.just_now)
    }
}

