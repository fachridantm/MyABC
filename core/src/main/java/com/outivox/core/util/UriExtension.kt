package com.outivox.core.util

import android.net.Uri

fun Uri.getQueryParameterString(key: String): String {
    return getQueryParameter(key).orEmpty()
}

fun Uri.getQueryParameterInt(key: String): Int {
    return getQueryParameter(key).orEmpty().ifEmpty { "0" }.toInt()
}

fun Uri.getQueryParameterIntOrNull(key: String): Int? {
    return getQueryParameter(key)?.ifEmpty { "0" }?.toIntOrNull()
}

fun Uri.getQueryParameterDouble(key: String): Double {
    return getQueryParameter(key).orEmpty().ifEmpty { "0.0" }.toDouble()
}

fun Uri.getQueryParameterDoubleOrNull(key: String): Double? {
    return getQueryParameter(key)?.ifEmpty { "0.0" }?.toDoubleOrNull()
}

fun Uri.getQueryParameterBoolean(key: String): Boolean {
    return getQueryParameter(key).orEmpty().ifEmpty { "false" }.equals("true", ignoreCase = true)
}

fun Uri.getQueryParameterBooleanOrNull(key: String): Boolean? {
    val value = getQueryParameter(key)?.ifEmpty { "false" }
    return value?.equals("true", ignoreCase = true)
}