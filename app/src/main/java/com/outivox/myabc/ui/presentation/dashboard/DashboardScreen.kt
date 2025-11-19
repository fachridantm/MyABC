package com.outivox.myabc.ui.presentation.dashboard

import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.outivox.core.bottomsheet.rememberBottomSheetFlow
import com.outivox.core.theme.MyABCTheme
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
import com.outivox.myabc.ui.presentation.dashboard.bottomsheet.navigation.NavRoute

data class DashboardScreenState(
    @DrawableRes val userAvatar: Int = 0,
    val userName: String = "",
    val totalBalance: String = "",
    val accountNumber: String = "",
    val menuList: List<MenuCardAttribute> = emptyList(),
    val paymentList: List<BillSectionResourceItemAttribute> = emptyList(),
    val favoriteList: List<FavoriteSectionResourceItemAttribute> = emptyList(),
)

sealed class DashboardScreenEvent {
    data object OnBackPressed : DashboardScreenEvent()
    data object OnNotificationClicked : DashboardScreenEvent()
    data object OnMenuClicked : DashboardScreenEvent()
    data object OnViewAllClicked : DashboardScreenEvent()
    data object OnManageClicked : DashboardScreenEvent()
    data object OnRepeatClicked : DashboardScreenEvent()
    data object OnBottomNavClicked : DashboardScreenEvent()
}

@Composable
fun DashboardScreen(
    state: DashboardScreenState = DashboardScreenState(),
    event: (DashboardScreenEvent) -> Unit = {},
) {
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        containerColor = Color.White,
        topBar = {
            DashboardScreenTopBar(
                userAvatar = state.userAvatar,
                userName = state.userName,
                onClickNotification = {
                    event(DashboardScreenEvent.OnNotificationClicked)
                },
            )
        },
        content = {
            DashboardScreenContent(
                modifier = Modifier.padding(it),
                state = state,
                event = event,
            )
        },
        bottomBar = {
            BottomNavBar(
                onClick = {
                    event(DashboardScreenEvent.OnBottomNavClicked)
                },
            )
        },
    )

    BottomSheetDashboardContainer(
        bottomSheetFlow = rememberBottomSheetFlow(
            startDestination = NavRoute.FirstRoute.route
        ),
        showBottomSheet = showBottomSheet,
        onCancel = { showBottomSheet = false }
    )

    BackHandler {
        event(DashboardScreenEvent.OnBackPressed)
    }
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
    LazyColumn(
        modifier = modifier,
    ) {
        item {
            SummaryCard(
                modifier = Modifier.padding(16.dp),
                balance = state.totalBalance,
                accountNumber = state.accountNumber,
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