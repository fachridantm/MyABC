package com.outivox.myabc.ui.presentation.dashboard

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.outivox.core.navigation.NotificationScreenDestination
import com.outivox.core.navigation.SettingScreenDestination
import com.outivox.core.navigation.TransactionScreenDestination
import com.outivox.core.theme.MyABCTheme
import com.outivox.core.util.LocalNavBackStack
import com.outivox.myabc.R
import com.outivox.myabc.ui.atoms.Avatar
import com.outivox.myabc.ui.molecules.MenuCardAttribute
import com.outivox.myabc.ui.molecules.SummaryCard
import com.outivox.myabc.ui.organisms.BillSection
import com.outivox.myabc.ui.organisms.BillSectionResourceItemAttribute
import com.outivox.myabc.ui.organisms.BottomNavBar
import com.outivox.myabc.ui.organisms.FavoriteSection
import com.outivox.myabc.ui.organisms.FavoriteSectionResourceItemAttribute
import com.outivox.myabc.ui.organisms.MenuGrid
import com.outivox.myabc.ui.organisms.MenuGridAttribute
import com.outivox.myabc.ui.presentation.dashboard.bottomsheet.BottomSheetDashboardContainer

private const val TAG = "DashboardScreen"

// TODO (Step 2.1): Complete Unidirectional Data Flow (UDF) State & Event contracts.
// State represents immutable snapshots flowing down; Events represent user actions flowing up.
data class DashboardScreenState(
    @DrawableRes val userAvatar: Int = 0,
    val userName: String = "",
    val totalBalance: String = "",
    val accountNumber: String = "",
    val isBalanceVisible: Boolean = true,
    val isLoading: Boolean = false,
    val userMessage: String? = null,
    val menuList: List<MenuCardAttribute> = emptyList(),
    val paymentList: List<BillSectionResourceItemAttribute> = emptyList(),
    val favoriteList: List<FavoriteSectionResourceItemAttribute> = emptyList(),
)

sealed class DashboardScreenEvent {
    data object OnLoadDashboard : DashboardScreenEvent()
    data object OnBackPressed : DashboardScreenEvent()
    data object OnMenuClicked : DashboardScreenEvent()
    data object OnViewAllClicked : DashboardScreenEvent()
    data object OnManageClicked : DashboardScreenEvent()
    data object OnRepeatClicked : DashboardScreenEvent()
    data object OnToggleBalanceVisibility : DashboardScreenEvent()
    data object OnNotificationClicked : DashboardScreenEvent()
    data class OnBottomNavClicked(val title: String) : DashboardScreenEvent()
    data object OnClearMessage : DashboardScreenEvent()
}

// TODO (Step 1.2): Hoist UI state in DashboardScreen and pass down to stateless children.
// TODO (Step 1.3): Flow vs StateFlow vs MutableState:
// 1. MutableState<T> (`remember { mutableStateOf(...) }`): fine-grained Compose UI state.
// 2. StateFlow<T>: hot reactive observable stream from ViewModels, collected safely with `collectAsStateWithLifecycle()`.
@Composable
fun DashboardScreen(
    state: DashboardScreenState = DashboardScreenState(),
) {
    val activity = LocalActivity.current
    val context = LocalContext.current
    val navBackStack = LocalNavBackStack.current

    var showBottomSheet by remember { mutableStateOf(false) }
    var localIsBalanceVisible by remember { mutableStateOf(state.isBalanceVisible) }
    val snackbarHostState = remember { SnackbarHostState() }

    // TODO (Step 3.2): Trigger initial dashboard data load using LaunchedEffect(Unit).
    // Executes once when entering composition; restarts only if key changes.
    LaunchedEffect(Unit) {
        Log.i(TAG, "DashboardScreen entered composition: Initializing dashboard data")
    }

    // TODO (Step 3.3): Display one-off feedback using SnackbarHostState and LaunchedEffect.
    LaunchedEffect(state.userMessage) {
        state.userMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        containerColor = Color.White,
        topBar = {
            DashboardScreenTopBar(
                userAvatar = state.userAvatar,
                userName = state.userName,
                onClickNotification = {
                    // TODO (Step 5.2): Navigate to inner-module NotificationScreenDestination using navBackStack.add(...)
                    runCatching {
                        navBackStack.add(NotificationScreenDestination)
                    }.onFailure {
                        Toast.makeText(context, "Failed to navigate", Toast.LENGTH_SHORT).show()
                        Log.e(TAG, it.message.orEmpty(), it)
                    }
                },
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = {
            DashboardScreenContent(
                modifier = Modifier.padding(it),
                state = state.copy(isBalanceVisible = localIsBalanceVisible),
                event = { screenEvent ->
                    when (screenEvent) {
                        DashboardScreenEvent.OnManageClicked -> {
                            Toast.makeText(context, "Manage Favorites", Toast.LENGTH_SHORT).show()
                        }

                        DashboardScreenEvent.OnMenuClicked -> {
                            showBottomSheet = true
                        }

                        DashboardScreenEvent.OnRepeatClicked -> {
                            Toast.makeText(context, "Repeat Transaction", Toast.LENGTH_SHORT).show()
                        }

                        DashboardScreenEvent.OnViewAllClicked -> {
                            // TODO (Step 5.2): Navigate to inner-module TransactionScreenDestination
                            runCatching {
                                navBackStack.add(TransactionScreenDestination)
                            }.onFailure {
                                Log.e(TAG, "Failed to navigate to transactions", it)
                            }
                        }

                        DashboardScreenEvent.OnBackPressed -> {
                            activity?.finishAndRemoveTask()
                        }

                        DashboardScreenEvent.OnToggleBalanceVisibility -> {
                            localIsBalanceVisible = !localIsBalanceVisible
                        }

                        DashboardScreenEvent.OnNotificationClicked -> {
                            navBackStack.add(NotificationScreenDestination)
                        }

                        is DashboardScreenEvent.OnBottomNavClicked -> {
                            when (screenEvent.title) {
                                "History" -> navBackStack.add(TransactionScreenDestination)
                                "Profile" -> navBackStack.add(SettingScreenDestination)
                                else -> Toast.makeText(context, screenEvent.title, Toast.LENGTH_SHORT).show()
                            }
                        }

                        DashboardScreenEvent.OnLoadDashboard -> {}
                        DashboardScreenEvent.OnClearMessage -> {}
                    }
                },
            )
        },
        bottomBar = {
            BottomNavBar(
                onClick = {
                    // Navigate to Profile/Settings on profile tab
                    runCatching {
                        navBackStack.add(SettingScreenDestination)
                    }.onFailure {
                        Toast.makeText(context, "Settings", Toast.LENGTH_SHORT).show()
                    }
                },
            )
        },
    )

    BottomSheetDashboardContainer(
        showBottomSheet = showBottomSheet,
        onCancel = { showBottomSheet = false }
    )
}

@Composable
private fun DashboardScreenTopBar(
    @DrawableRes userAvatar: Int,
    userName: String,
    onClickNotification: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Avatar(
            imageRes = userAvatar,
            size = 32.dp,
        )
        Text(
            modifier = Modifier.weight(1f),
            text = "Good Morning, $userName",
            fontSize = 18.sp,
        )
        Icon(
            modifier = Modifier.clickable(onClick = onClickNotification),
            imageVector = Icons.Filled.Notifications,
            contentDescription = null,
        )
    }
}

@Composable
private fun DashboardScreenContent(
    modifier: Modifier,
    state: DashboardScreenState,
    event: (DashboardScreenEvent) -> Unit,
) {
    BackHandler {
        event(DashboardScreenEvent.OnBackPressed)
    }
    LazyColumn(
        modifier = modifier,
    ) {
        item {
            SummaryCard(
                modifier = Modifier.padding(16.dp),
                balance = state.totalBalance,
                accountNumber = state.accountNumber,
                isBalanceVisible = state.isBalanceVisible,
                onToggleVisibility = {
                    event(DashboardScreenEvent.OnToggleBalanceVisibility)
                },
            )
        }
        item {
            MenuGrid(
                attribute = MenuGridAttribute(itemList = state.menuList),
                onClickMenu = {
                    event(DashboardScreenEvent.OnMenuClicked)
                }
            )
        }
        item {
            BillSection(
                modifier = Modifier.padding(16.dp),
                data = state.paymentList,
                onClickViewAll = {
                    event(DashboardScreenEvent.OnViewAllClicked)
                },
            )
        }
        item {
            FavoriteSection(
                modifier = Modifier.padding(16.dp),
                data = state.favoriteList,
                onClickManage = {
                    event(DashboardScreenEvent.OnManageClicked)
                },
                onClickRepeat = {
                    event(DashboardScreenEvent.OnRepeatClicked)
                },
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    MyABCTheme {
        DashboardScreen(
            state = DashboardScreenState(
                userAvatar = R.drawable.img_man_side_view,
                userName = "Alex",
                totalBalance = "$12,345.67",
                accountNumber = "...1234",
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
            ),
        )
    }
}