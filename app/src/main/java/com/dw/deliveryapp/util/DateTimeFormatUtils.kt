package com.dw.deliveryapp.util

import android.os.Build
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object DateTimeFormat {
    const val FORMAT_yyyy_MM_dd_HH = "yyyyMMddHH"
    const val FORMAT_yyyy_MM_DD_HH_mm_ss = "yyyy-MM-dd HH:mm:ss"
}

object DateTimeFormatUtil {
    fun formatStr(pattern: String, date: Date): String {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                DateTimeFormatter.ofPattern(pattern).format(
                    date.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime()
                )
            } else {
                SimpleDateFormat(pattern, Locale.getDefault()).format(date)
            }
        } catch (ignore: Exception) {
            ""
        }
    }

    fun convertFormat(oriDateTime: String, oriPattern: String, convertedPattern: String): String {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val oriFormatter = DateTimeFormatter.ofPattern(oriPattern)
                val dateTime = ZonedDateTime.parse(oriDateTime, oriFormatter)
                DateTimeFormatter.ofPattern(convertedPattern).format(
                    dateTime.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime()
                )
            } else {
                val oriDateFormat = SimpleDateFormat(oriPattern, Locale.getDefault())
                val datetime = oriDateFormat.parse(oriDateTime) ?: return ""
                SimpleDateFormat(convertedPattern, Locale.getDefault()).format(datetime)
            }
        } catch (ignore: Exception) {
            ""
        }
    }
}