package com.outivox.myabc.ui.navigation

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.outivox.core.navigation.DashboardScreenDestination
import com.outivox.core.navigation.LaunchableSampleScreenDestination
import com.outivox.core.navigation.NotificationScreenDestination
import com.outivox.core.navigation.SplashScreenDestination
import com.outivox.core.util.LocalNavController
import com.outivox.myabc.R
import com.outivox.myabc.ui.molecules.MenuCardAttribute
import com.outivox.myabc.ui.organisms.BillSectionResourceItemAttribute
import com.outivox.myabc.ui.organisms.FavoriteSectionResourceItemAttribute
import com.outivox.myabc.ui.organisms.NotificationResourceItemAttribute
import com.outivox.myabc.ui.organisms.NotificationSectionAttribute
import com.outivox.myabc.ui.presentation.dashboard.DashboardScreen
import com.outivox.myabc.ui.presentation.dashboard.DashboardScreenEvent
import com.outivox.myabc.ui.presentation.dashboard.DashboardScreenState
import com.outivox.myabc.ui.presentation.notification.NotificationScreen
import com.outivox.myabc.ui.presentation.notification.NotificationScreenEvent
import com.outivox.myabc.ui.presentation.notification.NotificationScreenState
import com.outivox.myabc.ui.presentation.sample.SampleScreen
import com.outivox.myabc.ui.presentation.splash.SplashScreen

private const val TAG = "MainNavGraph"

fun NavGraphBuilder.mainNavGraph() {
    composable<LaunchableSampleScreenDestination> {
        SampleScreen()
    }

    composable<SplashScreenDestination> {
        SplashScreen()
    }

    composable<DashboardScreenDestination> {
        val activity = LocalActivity.current
        val context = LocalContext.current
        val navController = LocalNavController.current
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
            event = { screenEvent ->
                when (screenEvent) {
                    is DashboardScreenEvent.OnNotificationClicked -> {
                        runCatching {
                            navController.navigate(NotificationScreenDestination)
                        }.onFailure {
                            Toast.makeText(context, "Failed to navigate", Toast.LENGTH_SHORT).show()
                            Log.e(TAG, it.message.orEmpty(), it)
                        }
                    }

                    DashboardScreenEvent.OnBottomNavClicked -> {
                        Toast.makeText(context, "Not implemented yet", Toast.LENGTH_SHORT).show()
                    }

                    DashboardScreenEvent.OnManageClicked -> {
                        Toast.makeText(context, "Not implemented yet", Toast.LENGTH_SHORT).show()
                    }

                    DashboardScreenEvent.OnMenuClicked -> {
                        Toast.makeText(context, "Not implemented yet", Toast.LENGTH_SHORT).show()
                    }

                    DashboardScreenEvent.OnRepeatClicked -> {
                        Toast.makeText(context, "Not implemented yet", Toast.LENGTH_SHORT).show()
                    }

                    DashboardScreenEvent.OnViewAllClicked -> {
                        Toast.makeText(context, "Not implemented yet", Toast.LENGTH_SHORT).show()
                    }

                    DashboardScreenEvent.OnBackPressed -> {
                        activity?.finishAndRemoveTask()
                    }
                }
            }
        )
    }

    composable<NotificationScreenDestination> {
        val navController = LocalNavController.current
        NotificationScreen(
            state = NotificationScreenState(
                notificationList = listOf(
                    NotificationSectionAttribute(
                        date = "TODAY",
                        itemList = listOf(
                            NotificationResourceItemAttribute(
                                iconRes = R.drawable.ic_card_outline,
                                label = "Card Purchase",
                                description = "$50.00 at Starbucks",
                                timestamp = "10:45 AM",
                            ),
                            NotificationResourceItemAttribute(
                                iconRes = R.drawable.ic_card_outline,
                                label = "New Device Login",
                                description = "From a device in London, UK",
                                timestamp = "9:30 AM",
                            ),
                        ),
                    ),
                    NotificationSectionAttribute(
                        date = "YESTERDAY",
                        itemList = listOf(
                            NotificationResourceItemAttribute(
                                iconRes = R.drawable.ic_card_outline,
                                label = "New Cashback Offer",
                                description = "Earn 5% back on dining.",
                                timestamp = "4:15 PM",
                            ),
                            NotificationResourceItemAttribute(
                                iconRes = R.drawable.ic_card_outline,
                                label = "Your e-Statement is Ready",
                                description = "Statement for October 2023",
                                timestamp = "9:02 AM",
                                isUnread = false,
                            ),
                        ),
                    ),
                    NotificationSectionAttribute(
                        date = "OCTOBER 15",
                        itemList = listOf(
                            NotificationResourceItemAttribute(
                                iconRes = R.drawable.ic_card_outline,
                                label = "Card Purchase",
                                description = "$12.50 at Starbucks",
                                timestamp = "11:55 AM",
                                isUnread = false,
                            ),
                        ),
                    ),
                ),
            ),
            event = { screenEvent ->
                when (screenEvent) {
                    NotificationScreenEvent.OnBackPressed -> {
                        navController.popBackStack()
                    }
                }
            }
        )
    }
}