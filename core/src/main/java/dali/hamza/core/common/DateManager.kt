package dali.hamza.core.common

import java.text.SimpleDateFormat
import java.util.*
import java.time.temporal.TemporalQueries.localTime


class DateManager {

    companion object {
        private val formatTime = SimpleDateFormat("hh:mm a")
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")

        fun adjustDateWithCurrentCET(date: String): Date {
            val cetDiff = getTimeCET()
            val nDate = format.parse(date)
            val calExpiration = Calendar.getInstance()
            calExpiration.time = nDate
            calExpiration.add(calExpiration.get(Calendar.HOUR), cetDiff)
            return calExpiration.time
        }


        fun getTimeCET(date: Date? = now()): Int {

            val nowFormat = SimpleDateFormat("Z").format(date)
            val signe = if (nowFormat.contains("+")) 1 else -1
            return nowFormat.substring(1, 3).toInt() * signe
        }

        fun formatToTime(
            dateToFormat: String,
            formatSource: SimpleDateFormat? = null
        ): String {
            val _format = formatSource ?: format
            val date = _format.parse(dateToFormat)

            return formatTime.format(date)


        }

        fun now(): Date {
            val calendar = Calendar.getInstance()
            return calendar.time
        }

        fun difference2Date(d1: Date, d2: Date = now()): DateExpiration {
            val diff: Long = d2.time - d1.time
            val seconds = diff / 1000
            val minutes = seconds / 60
            val hours = minutes / 60
            val days = hours / 24
            return DateExpiration(
                days = days.toInt(),
                hours = hours.toInt(),
                minutes = minutes.toInt(),
                seconds = seconds.toInt(),
            )

        }
    }
}

data class DateExpiration(
    val days: Int,
    val hours: Int,
    val minutes: Int,
    val seconds: Int,
)