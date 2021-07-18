package com.dw.deliveryapp.util

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

fun readJsonFromResources(clz: Class<*>, fileName: String): String {
    var inputStream: InputStream? = null
    try {
        inputStream =
            clz.classLoader?.getResourceAsStream(fileName)
        val builder = StringBuilder()
        val reader = BufferedReader(InputStreamReader(inputStream))

        var str: String? = reader.readLine()
        while (str != null) {
            builder.append(str)
            str = reader.readLine()
        }
        return builder.toString()
    } finally {
        inputStream?.close()
    }
}
