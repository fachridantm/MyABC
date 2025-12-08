package com.outivox.myabc.core.util

fun PlatformUri.getQueryParameterString(key: String): String {
    return getUriQueryParameter(key).orEmpty()
}

fun PlatformUri.getQueryParameterInt(key: String): Int {
    return getUriQueryParameter(key).orEmpty().ifEmpty { "0" }.toInt()
}

fun PlatformUri.getQueryParameterIntOrNull(key: String): Int? {
    return getUriQueryParameter(key)?.ifEmpty { "0" }?.toIntOrNull()
}

fun PlatformUri.getQueryParameterDouble(key: String): Double {
    return getUriQueryParameter(key).orEmpty().ifEmpty { "0.0" }.toDouble()
}

fun PlatformUri.getQueryParameterDoubleOrNull(key: String): Double? {
    return getUriQueryParameter(key)?.ifEmpty { "0.0" }?.toDoubleOrNull()
}

fun PlatformUri.getQueryParameterBoolean(key: String): Boolean {
    return getUriQueryParameter(key).orEmpty().ifEmpty { "false" }.equals("true", ignoreCase = true)
}

fun PlatformUri.getQueryParameterBooleanOrNull(key: String): Boolean? {
    val value = getUriQueryParameter(key)?.ifEmpty { "false" }
    return value?.equals("true", ignoreCase = true)
}