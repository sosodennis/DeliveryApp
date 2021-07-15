package com.dw.deliveryapp.util

import java.text.DecimalFormat

class AmountUtils {
    object AmountFormat {
        const val DEFAULT = "$#,###.##"
    }

    companion object {
        fun format(amt: Double, numFormat: String = AmountFormat.DEFAULT): String {
            return DecimalFormat(numFormat).format(amt)
        }

        fun parseDouble(amt: String, numFormat: String = AmountFormat.DEFAULT): Double {
            return try {
                DecimalFormat(numFormat).parse(amt)?.toDouble() ?: 0.0
            } catch (e: Exception) {
                0.0
            }
        }
    }
}