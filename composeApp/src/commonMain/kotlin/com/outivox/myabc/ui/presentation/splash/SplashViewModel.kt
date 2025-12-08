package com.outivox.myabc.ui.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.outivox.myabc.core.util.JsonExtensions.toJson
import com.outivox.myabc.core.util.PrintLog
import com.outivox.myabc.core.util.UiState
import com.outivox.myabc.core.util.UiState.Companion.default
import com.outivox.myabc.core.util.UiState.Companion.error
import com.outivox.myabc.core.util.UiState.Companion.loading
import com.outivox.myabc.core.util.UiState.Companion.success
import com.outivox.myabc.core.util.data
import com.outivox.myabc.core.util.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    private val tag = this@SplashViewModel::class.simpleName.orEmpty()

    private val _initState = MutableStateFlow<UiState<Map<String, String>>>(default())
    val initState = _initState.asStateFlow()

    private val dummyData
        get() = mapOf(
            "key1" to "value1",
            "key2" to "value2",
            "key3" to "value3"
        )

    private val flowDummyDataUseCase
        get() = flow {
            runCatching {
                delay(1500L)
                emit(dummyData)
            }.onFailure { exception ->
                PrintLog.e(tag, exception.message.orEmpty(), exception)
                throw exception
            }.onSuccess {
                PrintLog.d(tag, "Execution retrieved successfully")
            }
        }.flowOn(Dispatchers.IO).mapNotNull { it }

    fun init() = viewModelScope.launch {
        flowDummyDataUseCase
            .map { success(it) }
            .onStart { emit(loading()) }
            .catch { emit(error(it, it.message.orEmpty())) }
            .collect { uiState ->
                val data = if (uiState.isSuccess) uiState.data?.toJson() else uiState
                PrintLog.i(tag, "initState: Fresh collected uiState: $data")
                _initState.update { uiState }
            }
    }

    fun resetInitState() {
        _initState.update { default() }
    }
}