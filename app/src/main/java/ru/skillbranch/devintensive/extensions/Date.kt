package ru.skillbranch.devintensive.extensions

import java.util.*
import java.text.SimpleDateFormat

enum class TimeUnits(
        val quantity: Long,
        val pluralOne: String,
        val pluralTwo: String,
        val pluralFive: String
) {
    // Здесь предполагается, что plural отвечает на вопрос "Когда?"
    SECOND(1000, "секунду", "секунды", "секунд"),
    MINUTE(60 * SECOND.quantity, "минуту", "минуты", "минут"),
    HOUR(60 * MINUTE.quantity, "час", "часа", "часов"),
    DAY(24 * HOUR.quantity, "день", "дня", "дней");

    fun plural(value: Int): String {
        val lastDigit = value % 10
        val pluralString =
                if ((value > 10 && value < 20) || lastDigit == 0 || lastDigit >= 5) pluralFive
                else if (lastDigit == 1) pluralOne
                else pluralTwo

        return "$value $pluralString"
    }
}

fun Date.format(pattern: String? = null): String {
    return SimpleDateFormat(pattern ?: "HH:mm:ss dd.MM.yy", Locale.US).format(this)
}

fun Date.add(value: Int, units: TimeUnits): Date {
    this.time += units.quantity * value
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val seconds: Long = (date.time - this.time) / 1000
    val minutes: Int = (Math.abs(seconds) / 60).toInt()
    val hours: Int = (Math.abs(seconds) / 60 / 60).toInt()
    val days: Int = (Math.abs(seconds) / 60 / 60 / 24).toInt()

    val absDelta: Long = Math.abs(seconds)

    if (absDelta <= 1)
        return "только что"
    else if (absDelta <= 45)
        return if (seconds > 0) "несколько секунд назад" else "через несколько секунд"
    else if (absDelta <= 75)
        return if (seconds > 0) "минуту назад" else "через минуту"
    else if (absDelta < 45 * 60)
        return if (seconds > 0) "${TimeUnits.MINUTE.plural(minutes)} назад" else "через ${TimeUnits.MINUTE.plural(minutes)}"
    else if (absDelta < 75 * 60)
        return if (seconds > 0) "час назад" else "через час"
    else if (absDelta < 22 * 60 * 60)
        return if (seconds > 0) "${TimeUnits.HOUR.plural(hours)} назад" else "через ${TimeUnits.HOUR.plural(hours)}"
    else if (absDelta < 26 * 60 * 60)
        return if (seconds > 0) "день назад" else "через день"
    else if (absDelta < 360 * 24 * 60 * 60)
        return if (seconds > 0) "${TimeUnits.DAY.plural(days)} назад" else "через ${TimeUnits.DAY.plural(days)}"
    else
        return if (seconds > 0) "более года назад" else "более чем через год"
}