package com.outivox.core.navigation

import androidx.lifecycle.ViewModel
import com.outivox.core.deeplink.Deeplink
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor() : ViewModel() {
    // Deeplink
    private val _deeplink = MutableStateFlow<Deeplink?>(null)
    val deeplink = _deeplink.asStateFlow()

    fun saveDeeplink(deeplinkData: Deeplink) {
        _deeplink.update { deeplinkData }
    }

    fun resetDeeplink() {
        _deeplink.update { null }
    }
}