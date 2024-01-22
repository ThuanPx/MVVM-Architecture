package com.thuanpx.view_mvvm_architecture.utils.extension.string

import android.util.Patterns
import com.thuanpx.view_mvvm_architecture.app.Constant
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.regex.Pattern

@Throws(ParseException::class)
fun String.toDate(format: String): Date {
    val parser = SimpleDateFormat(format, Locale.getDefault())
    return parser.parse(this)
}

fun String?.isBlank(): Boolean {
    return this == null || isEmpty()
}

@Throws(ParseException::class)
fun String.toDateWithFormat(inputFormat: String, outputFormat: String): String {
    val gmtTimeZone = TimeZone.getTimeZone(Constant.KTEXT_TIME_ZONE_UTC)
    val inputDateTimeFormat = SimpleDateFormat(inputFormat, Locale.getDefault())
    inputDateTimeFormat.timeZone = gmtTimeZone

    val outputDateTimeFormat = SimpleDateFormat(outputFormat, Locale.getDefault())
    outputDateTimeFormat.timeZone = gmtTimeZone
    return outputDateTimeFormat.format(inputDateTimeFormat.parse(this))
}

@Throws(ParseException::class)
fun String.toDateWithFormat(
    inputFormat: String,
    outputFormat: String,
    outputTimeZone: TimeZone = TimeZone.getDefault()
): String {
    val gmtTimeZone = TimeZone.getTimeZone(Constant.KTEXT_TIME_ZONE_UTC)
    val inputDateTimeFormat = SimpleDateFormat(inputFormat, Locale.getDefault())
    inputDateTimeFormat.timeZone = gmtTimeZone

    val outputDateTimeFormat = SimpleDateFormat(outputFormat, Locale.getDefault())
    outputDateTimeFormat.timeZone = outputTimeZone
    return outputDateTimeFormat.format(inputDateTimeFormat.parse(this))
}

fun String.validWithPattern(pattern: Pattern): Boolean {
    return pattern.matcher(toLowerCase()).find()
}

fun String.validWithPattern(regex: String): Boolean {
    return Pattern.compile(regex).matcher(this).find()
}

fun String.removeWhitespaces(): String {
    return this.replace("[\\s-]*".toRegex(), "")
}

fun String.toIntOrZero() = if (this.toIntOrNull() == null) 0 else this.toInt()

fun String.isNumeric(): Boolean = this matches "-?\\d+(\\.\\d+)?".toRegex()

fun String.containsWebUrl() = Patterns.WEB_URL.matcher(this).find()

fun String?.nullToEmpty(): String = this ?: ""

fun String?.isNullOrZero() = this == "0" || this == null
