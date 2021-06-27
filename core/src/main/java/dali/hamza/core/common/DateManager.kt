package dali.hamza.core.common

import java.text.SimpleDateFormat

class DateManager {

    companion object {
        private val formatTime = SimpleDateFormat("hh:mm a")
        private val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

        fun formatToTime(
            dateToFormat: String,
            formatSource: SimpleDateFormat? = null
        ): String {
            val _format = formatSource ?: format
            val date = _format.parse(dateToFormat)

            return formatTime.format(date)


        }
    }
}