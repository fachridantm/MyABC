package com.outivox.myabc.ui.presentation.setting.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.outivox.core.util.UiState
import com.outivox.core.util.UiState.Companion.default
import com.outivox.core.util.UiState.Companion.error
import com.outivox.core.util.UiState.Companion.loading
import com.outivox.core.util.UiState.Companion.success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingDetailItem(
    val title: String,
    val description: String = "",
    val fields: Map<String, String> = emptyMap(),
)

data class SettingDetailScreenState(
    val title: String = "",
    val description: String = "",
    val fields: Map<String, String> = emptyMap(),
    val isLoading: Boolean = false,
    val userMessage: String? = null,
)

sealed class SettingDetailEvent {
    data class LoadDetail(val title: String) : SettingDetailEvent()
    data class UpdateField(val key: String, val value: String) : SettingDetailEvent()
    data object SaveChanges : SettingDetailEvent()
    data object ClearMessage : SettingDetailEvent()
}

@HiltViewModel
class SettingDetailViewModel @Inject constructor() : ViewModel() {

    private val tag = "SettingDetailViewModel"

    // =========================================================================
    // TODO (Step 7.1): [Data Layer in ViewModel]
    // Define the data source directly inside the ViewModel (similar to SplashViewModel).
    // In enterprise architectures, this simulates a remote/local data source for setting details.
    // =========================================================================
    private val dummySettingDetailData = mapOf(
        "Contact Details" to SettingDetailItem(
            title = "Contact Details",
            description = "Manage your verified phone numbers and communication emails.",
            fields = linkedMapOf(
                "Phone Number" to "+62 812-3456-7890",
                "Email" to "alex.doe@email.com",
                "Emergency Contact" to "+62 811-9876-5432",
            ),
        ),
        "Mailing Address" to SettingDetailItem(
            title = "Mailing Address",
            description = "Your primary billing and card delivery postal address.",
            fields = linkedMapOf(
                "Street Address" to "Jl. Jend. Sudirman No. 1",
                "City" to "Jakarta Pusat",
                "Postal Code" to "10220",
            ),
        ),
        "Change Password" to SettingDetailItem(
            title = "Change Password",
            description = "Update your digital banking authentication password.",
            fields = linkedMapOf(
                "Current Password" to "••••••••",
                "New Password" to "",
                "Confirm Password" to "",
            ),
        ),
        "Manage Biometrics" to SettingDetailItem(
            title = "Manage Biometrics",
            description = "Configure fingerprint and facial recognition login preferences.",
            fields = linkedMapOf(
                "Biometric Status" to "Active (Fingerprint registered)",
                "Fallback PIN" to "Enabled",
            ),
        ),
        "Linked Accounts & Devices" to SettingDetailItem(
            title = "Linked Accounts & Devices",
            description = "Review mobile devices and third-party integrations with access.",
            fields = linkedMapOf(
                "Active Device" to "Pixel 7a (This Device)",
                "Last Active" to "Today, 10:45 AM",
            ),
        ),
        "Notification" to SettingDetailItem(
            title = "Notification",
            description = "Control real-time transaction alerts and marketing promos.",
            fields = linkedMapOf(
                "Push Notifications" to "Enabled",
                "Email Statements" to "Monthly",
            ),
        ),
        "Theme" to SettingDetailItem(
            title = "Theme",
            description = "Customize dark mode and application theme preferences.",
            fields = linkedMapOf(
                "Theme Mode" to "System Default",
                "High Contrast" to "Off",
            ),
        ),
    )

    // =========================================================================
    // TODO (Step 7.2): [Domain Layer in ViewModel - Simple UseCase]
    // Encapsulate business logic in a reactive Kotlin Flow UseCase.
    // Emits asynchronously on Dispatchers.IO with simulated network latency and error handling.
    // =========================================================================
    private fun fetchSettingDetailUseCase(title: String) = flow {
        runCatching {
            delay(300L) // Simulate network/DB latency
            val detail = dummySettingDetailData[title] ?: SettingDetailItem(
                title = title,
                description = "Configuration details for $title",
                fields = mapOf("Status" to "Active"),
            )
            emit(detail)
        }.onFailure { exception ->
            Log.e(tag, "Failed to fetch setting detail for $title: ${exception.message}", exception)
            throw exception
        }
    }.flowOn(Dispatchers.IO)

    // =========================================================================
    // TODO (Step 7.3): [MVI + MVVM Presentation Layer]
    // 1. Expose StateFlow<UiState<SettingDetailItem>> and StateFlow<SettingDetailScreenState>.
    // 2. Implement the MVI single entry-point `onEvent(event: SettingDetailEvent)`.
    // 3. Process intents and update state immutably using `_screenState.update { ... }`.
    // =========================================================================
    private val _uiState = MutableStateFlow<UiState<SettingDetailItem>>(default())
    val uiState: StateFlow<UiState<SettingDetailItem>> = _uiState.asStateFlow()

    private val _screenState = MutableStateFlow(SettingDetailScreenState())
    val screenState: StateFlow<SettingDetailScreenState> = _screenState.asStateFlow()

    fun onEvent(event: SettingDetailEvent) {
        when (event) {
            is SettingDetailEvent.LoadDetail -> loadDetail(event.title)
            is SettingDetailEvent.UpdateField -> updateField(event.key, event.value)
            is SettingDetailEvent.SaveChanges -> saveChanges()
            is SettingDetailEvent.ClearMessage -> _screenState.update { it.copy(userMessage = null) }
        }
    }

    private fun loadDetail(title: String) {
        viewModelScope.launch {
            fetchSettingDetailUseCase(title)
                .map { success(it) }
                .onStart {
                    emit(loading())
                    _screenState.update { it.copy(title = title, isLoading = true) }
                }
                .catch { exception ->
                    emit(error(exception, exception.message.orEmpty()))
                    _screenState.update { it.copy(isLoading = false, userMessage = "Failed to load $title") }
                }
                .collect { result ->
                    _uiState.update { result }
                    if (result is UiState.Success) {
                        val item = result.data
                        _screenState.update {
                            it.copy(
                                title = item.title,
                                description = item.description,
                                fields = item.fields,
                                isLoading = false,
                            )
                        }
                    }
                }
        }
    }

    private fun updateField(key: String, value: String) {
        _screenState.update { current ->
            val updated = current.fields.toMutableMap().apply { put(key, value) }
            current.copy(fields = updated)
        }
    }

    private fun saveChanges() {
        val currentTitle = _screenState.value.title
        _screenState.update {
            it.copy(userMessage = "$currentTitle changes saved successfully!")
        }
    }
}
