package com.outivox.myabc.core.util

import kotlinx.serialization.json.Json

object JsonExtensions {
    const val TAG = "JsonExtensions"

    inline fun <reified T> T.toJson() = runCatching {
        val json = Json {
            encodeDefaults = true
            ignoreUnknownKeys = true
            isLenient = true
            prettyPrint = true
        }
        json.encodeToString<T>(this)
    }.onFailure { throwable ->
        PrintLog.e(TAG, throwable.message.orEmpty(), throwable)
    }.getOrNull().orEmpty()

    inline fun <reified T> String?.fromJson() = runCatching {
        val json = Json {
            encodeDefaults = true
            ignoreUnknownKeys = true
            isLenient = true
            prettyPrint = true
        }
        json.decodeFromString<T>(this.orEmpty())
    }.onFailure { throwable ->
        PrintLog.e(TAG, throwable.message.orEmpty(), throwable)
    }.getOrNull()
}
