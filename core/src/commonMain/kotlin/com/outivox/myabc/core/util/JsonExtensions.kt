package com.outivox.myabc.core.util

import android.net.Uri
import android.util.Log
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule

object JsonExtensions {
    const val TAG = "JsonExtensions"

    inline fun <reified T> T.toJson() = runCatching {
        val json = Json {
            encodeDefaults = true
            ignoreUnknownKeys = true
            isLenient = true
            prettyPrint = true
            serializersModule = SerializersModule {
                contextual(Uri::class, UriSerializer)
            }
        }
        json.encodeToString<T>(this)
    }.onFailure { throwable ->
        Log.e(TAG, throwable.message.orEmpty(), throwable)
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
        Log.e(TAG, throwable.message.orEmpty(), throwable)
    }.getOrNull()
}

object UriSerializer : KSerializer<Uri> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Uri", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Uri) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): Uri {
        return Uri.parse(decoder.decodeString())
    }
}
