package com.outivox.core.util

sealed class UiState<out T> {
    data object Default : UiState<Nothing>()

    data object Loading : UiState<Nothing>()

    data class Success<out T>(
        val data: T,
    ) : UiState<T>()

    data class Error(
        val throwable: Throwable?,
        val message: String,
        val statusCode: Int? = null,
        val errorCode: String? = null,
    ) : UiState<Nothing>()

    companion object Companion {
        const val INVISIBE = 0f
        const val VISIBLE = 1f

        fun <T> default(): UiState<T> = Default

        fun <T> loading(): UiState<T> = Loading

        fun <T> success(data: T): UiState<T> = Success(data = data)

        fun <T> error(
            error: Throwable?,
            message: String,
            statusCode: Int? = null,
            errorCode: String? = null,
        ): UiState<T> = Error(
            throwable = error,
            message = message,
            statusCode = statusCode,
            errorCode = errorCode
        )
    }
}