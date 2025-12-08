package com.outivox.myabc.core.util

val <T> UiState<T>.data: T?
    get() = runCatching {
        (this as? UiState.Success)?.data
    }.getOrNull()

val <T> UiState<T>.error
    get() = runCatching {
        (this as? Error)
    }.getOrNull()

val <T> UiState<T>.isLoading get() = this is UiState.Loading

val <T> UiState<T>.isSuccess get() = this is UiState.Success

val <T> UiState<T>.isError get() = this is UiState.Error

val <T> UiState<T>.isDefault get() = this is UiState.Default