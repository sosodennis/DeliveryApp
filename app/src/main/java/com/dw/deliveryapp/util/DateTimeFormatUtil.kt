package com.dw.deliveryapp.util

import android.os.Build
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

object DateTimeFormat {
    const val FORMAT_YYYY_MM_DD_HH = "yyyyMMddHH"
}

class DateTimeFormatUtil {
    companion object {
        fun formatStr(pattern: String, date: Date): String {
            return try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    DateTimeFormatter.ofPattern(pattern).format(
                        date.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                    )
                } else {
                    SimpleDateFormat(pattern, Locale.getDefault()).format(date)
                }
            } catch (ignore: Exception) {
                ""
            }
        }
    }
}