package com.outivox.myabc.ui.presentation.dashboard

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.outivox.core.util.UiState
import com.outivox.core.util.UiState.Companion.default
import com.outivox.core.util.UiState.Companion.error
import com.outivox.core.util.UiState.Companion.loading
import com.outivox.core.util.UiState.Companion.success
import com.outivox.myabc.R
import com.outivox.myabc.ui.molecules.MenuCardAttribute
import com.outivox.myabc.ui.organisms.BillSectionResourceItemAttribute
import com.outivox.myabc.ui.organisms.FavoriteSectionResourceItemAttribute
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

@HiltViewModel
class DashboardViewModel @Inject constructor() : ViewModel() {

    private val tag = "DashboardViewModel"

    // =========================================================================
    // [Data Layer in ViewModel]
    // Simulated remote/local repository data source
    // =========================================================================
    private val dummyDashboardData
        get() = DashboardScreenState(
            userAvatar = R.drawable.img_man_side_view,
            userName = "Alex",
            totalBalance = "$12,345.67",
            accountNumber = "...1234",
            isBalanceVisible = true,
            menuList = listOf(
                MenuCardAttribute(
                    iconRes = R.drawable.ic_transfer,
                    label = "Transfer",
                ),
                MenuCardAttribute(
                    iconRes = R.drawable.ic_wallet,
                    label = "Deposit",
                ),
                MenuCardAttribute(
                    iconRes = R.drawable.ic_receipt,
                    label = "Pay Bills",
                ),
                MenuCardAttribute(
                    iconRes = R.drawable.ic_menu_outline,
                    label = "More",
                ),
            ),
            paymentList = listOf(
                BillSectionResourceItemAttribute(
                    iconRes = R.drawable.ic_netflix_square,
                    label = "Netflix",
                    description = "Due: Oct 28",
                    price = "$15.49",
                    iconBackgroundColor = Color.Magenta,
                ),
                BillSectionResourceItemAttribute(
                    iconRes = R.drawable.ic_light_bulb,
                    label = "City Power",
                    description = "Due: Nov 02",
                    price = "$78.20",
                    iconBackgroundColor = Color.Yellow,
                ),
            ),
            favoriteList = listOf(
                FavoriteSectionResourceItemAttribute(
                    imageRes = R.drawable.img_mom_portrait,
                    label = "Mom",
                    description = "Last transfer: $50.00",
                ),
                FavoriteSectionResourceItemAttribute(
                    imageRes = R.drawable.img_landlord_portrait,
                    label = "Landlord",
                    description = "Last transfer: $1200.00",
                ),
            ),
        )

    // =========================================================================
    // [Domain Layer in ViewModel - Simple UseCase]
    // Reactive Flow UseCase executing asynchronously on Dispatchers.IO
    // =========================================================================
    private val getDashboardDataUseCase
        get() = flow {
            runCatching {
                delay(200L)
                emit(dummyDashboardData)
            }.onFailure { exception ->
                Log.e(tag, "Failed to load dashboard data: ${exception.message}", exception)
                throw exception
            }
        }.flowOn(Dispatchers.IO)

    // =========================================================================
    // [MVI + MVVM Presentation Layer]
    // MVVM: Reactive StateFlow observable state stream
    // MVI: Single entry-point intent reducer handling DashboardScreenEvent
    // =========================================================================
    private val _uiState = MutableStateFlow<UiState<DashboardScreenState>>(default())
    val uiState: StateFlow<UiState<DashboardScreenState>> = _uiState.asStateFlow()

    private val _screenState = MutableStateFlow(dummyDashboardData)
    val screenState: StateFlow<DashboardScreenState> = _screenState.asStateFlow()

    init {
        loadDashboardData()
    }

    fun onEvent(event: DashboardScreenEvent) {
        when (event) {
            DashboardScreenEvent.OnLoadDashboard -> loadDashboardData()
            DashboardScreenEvent.OnToggleBalanceVisibility -> {
                _screenState.update { it.copy(isBalanceVisible = !it.isBalanceVisible) }
            }
            DashboardScreenEvent.OnRepeatClicked -> {
                _screenState.update { it.copy(userMessage = "Transfer to Mom repeated successfully!") }
            }
            DashboardScreenEvent.OnManageClicked -> {
                _screenState.update { it.copy(userMessage = "Opening Favorites Manager") }
            }
            DashboardScreenEvent.OnClearMessage -> {
                _screenState.update { it.copy(userMessage = null) }
            }
            else -> {}
        }
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            getDashboardDataUseCase
                .map { success(it) }
                .onStart { emit(loading()) }
                .catch { emit(error(it, it.message.orEmpty())) }
                .collect { result ->
                    _uiState.update { result }
                    if (result is UiState.Success) {
                        _screenState.update { result.data }
                    }
                }
        }
    }
}
