package com.outivox.myabc.core.navigation

import androidx.lifecycle.ViewModel
import com.outivox.myabc.core.deeplink.Deeplink
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NavigationViewModel : ViewModel() {
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