package kmp.parth.findtime

import io.github.aakira.napier.Napier
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class TimeZoneHelperImpl : TimeZoneHelper {
    override fun getTimeZoneStrings(): List<String> {
        return TimeZone.availableZoneIds.sorted() // Get a sorted list of available timezones
    }

    override fun currentTime(): String {
        val currentMoment: Instant = Clock.System.now() // Get the current time
        val dateTime: LocalDateTime =
            currentMoment.toLocalDateTime(TimeZone.currentSystemDefault()) // Convert the time to a LocalDateTime object
        return formatDateTime(dateTime) // Format and return the time
    }

    override fun currentTimeZone(): String {
        val currentTimezone = TimeZone.currentSystemDefault()
        return currentTimezone.toString()
    }

    override fun hoursFromTimeZone(otherTimeZoneId: String): Double {
        // Return the number of hours from given timezone
        val currentTimeZone = TimeZone.currentSystemDefault()
        val currentUTCInstant: Instant = Clock.System.now() // Get the current UTC time
        val currentDateTime: LocalDateTime =
            currentUTCInstant.toLocalDateTime(currentTimeZone) // DateTime in current timezone
        val otherTimeZone = TimeZone.of(otherTimeZoneId)
        val otherDateTime: LocalDateTime =
            currentUTCInstant.toLocalDateTime(otherTimeZone) // DateTime in other timezone
        return abs((currentDateTime.hour - otherDateTime.hour) * 1.0)
    }

    override fun getTime(timezoneId: String): String {
        val timezone = TimeZone.of(timezoneId) // Timezone with given ID
        val currentMoment: Instant = Clock.System.now() // Get current time instant
        val dateTime: LocalDateTime =
            currentMoment.toLocalDateTime(timezone) // Convert current time to given timezone
        return formatDateTime(dateTime) // Format and return the time
    }

    override fun getDate(timezoneId: String): String {
        val timezone = TimeZone.of(timezoneId)
        val currentMoment: Instant = Clock.System.now()
        val dateTime: LocalDateTime = currentMoment.toLocalDateTime(timezone)
        return "${dateTime.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }}, " +
                "${
                    dateTime.month.name.lowercase().replaceFirstChar { it.uppercase() }
                } ${dateTime.date.dayOfMonth}"
    }

    override fun search(startHour: Int, endHour: Int, timezoneStrings: List<String>): List<Int> {
        // Create a list to store valid hours
        val validHours = mutableListOf<Int>()
        val timeRange = IntRange(max(0, startHour), min(23, endHour))
        val currentTimeZone = TimeZone.currentSystemDefault()
        // Check each hour in 0-23 range if it's valid for all timezones
        for (hour in timeRange) {
            var isValidHour = false
            // Check if this hour works for all timezones
            for (zone in timezoneStrings) {
                val timezone = TimeZone.of(zone)
                if (timezone == currentTimeZone) continue
                if (!isValid(
                        timeRange = timeRange,
                        hour = hour,
                        currentTimeZone = currentTimeZone,
                        otherTimeZone = timezone
                    )
                ) {
                    Napier.d("Hour $hour is not valid for time range")
                    isValidHour = false
                } else {
                    Napier.d("Hour $hour is Valid for time range")
                    isValidHour = true
                }
            }
            if (isValidHour) {
                validHours.add(hour)
            }
        }

        return validHours
    }

    private fun isValid(
        timeRange: IntRange,
        hour: Int,
        currentTimeZone: TimeZone,
        otherTimeZone: TimeZone
    ): Boolean {
        if (hour !in timeRange) return false

        val currentUTCInstant: Instant = Clock.System.now()
        val otherDateTime: LocalDateTime = currentUTCInstant.toLocalDateTime(otherTimeZone)
        val otherDateTimeWithHour = LocalDateTime(
            year = otherDateTime.year,
            monthNumber = otherDateTime.monthNumber,
            dayOfMonth = otherDateTime.dayOfMonth,
            hour = hour,
            minute = 0,
            second = 0,
            nanosecond = 0
        )

        val localInstant = otherDateTimeWithHour.toInstant(currentTimeZone)
        val convertedTime = localInstant.toLocalDateTime(otherTimeZone)
        Napier.d("Hour $hour in Time Range ${otherTimeZone.id} is ${convertedTime.hour}")

        return convertedTime.hour in timeRange
    }

    fun formatDateTime(dateTime: LocalDateTime): String {
        val stringBuilder = StringBuilder()
        var hour = dateTime.hour % 12
        val minute = dateTime.minute

        if (hour == 0) hour = 12
        val amPm = if (dateTime.hour < 12) " am" else " pm"

        stringBuilder.append(hour.toString())
        stringBuilder.append(":")

        if (minute < 10) stringBuilder.append('0')
        stringBuilder.append((minute.toString()))
        stringBuilder.append(amPm)

        return stringBuilder.toString()
    }
}