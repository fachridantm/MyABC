package com.outivox.myabc.ui.navigation

import androidx.compose.ui.graphics.Color
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.outivox.myabc.core.navigation.DashboardScreenDestination
import com.outivox.myabc.core.navigation.LaunchableSampleScreenDestination
import com.outivox.myabc.core.navigation.NotificationScreenDestination
import com.outivox.myabc.core.navigation.SplashScreenDestination
import com.outivox.myabc.generated.resources.Res
import com.outivox.myabc.generated.resources.ic_card_outline
import com.outivox.myabc.generated.resources.ic_light_bulb
import com.outivox.myabc.generated.resources.ic_menu_outline
import com.outivox.myabc.generated.resources.ic_netflix_square
import com.outivox.myabc.generated.resources.ic_receipt
import com.outivox.myabc.generated.resources.ic_transfer
import com.outivox.myabc.generated.resources.ic_wallet
import com.outivox.myabc.generated.resources.img_landlord_portrait
import com.outivox.myabc.generated.resources.img_man_side_view
import com.outivox.myabc.generated.resources.img_mom_portrait
import com.outivox.myabc.ui.molecules.MenuCardAttribute
import com.outivox.myabc.ui.organisms.BillSectionResourceItemAttribute
import com.outivox.myabc.ui.organisms.FavoriteSectionResourceItemAttribute
import com.outivox.myabc.ui.organisms.NotificationResourceItemAttribute
import com.outivox.myabc.ui.organisms.NotificationSectionAttribute
import com.outivox.myabc.ui.presentation.dashboard.DashboardScreen
import com.outivox.myabc.ui.presentation.dashboard.DashboardScreenState
import com.outivox.myabc.ui.presentation.notification.NotificationScreen
import com.outivox.myabc.ui.presentation.notification.NotificationScreenState
import com.outivox.myabc.ui.presentation.sample.SampleScreen
import com.outivox.myabc.ui.presentation.splash.SplashScreen

private const val TAG = "MainNavGraph"

fun EntryProviderScope<NavKey>.mainNavGraph() {
    entry<LaunchableSampleScreenDestination> {
        SampleScreen()
    }

    entry<SplashScreenDestination> {
        SplashScreen()
    }

    entry<DashboardScreenDestination> {
        DashboardScreen(
            state = DashboardScreenState(
                userAvatar = Res.drawable.img_man_side_view,
                userName = "Alex",
                totalBalance = "$12,345.67",
                accountNumber = "...1234",
                menuList = listOf(
                    MenuCardAttribute(
                        iconRes = Res.drawable.ic_transfer,
                        label = "Transfer",
                    ),
                    MenuCardAttribute(
                        iconRes = Res.drawable.ic_wallet,
                        label = "Deposit",
                    ),
                    MenuCardAttribute(
                        iconRes = Res.drawable.ic_receipt,
                        label = "Pay Bills",
                    ),
                    MenuCardAttribute(
                        iconRes = Res.drawable.ic_menu_outline,
                        label = "More",
                    ),
                ),
                paymentList = listOf(
                    BillSectionResourceItemAttribute(
                        iconRes = Res.drawable.ic_netflix_square,
                        label = "Netflix",
                        description = "Due: Oct 28",
                        price = "$15.49",
                        iconBackgroundColor = Color.Magenta,
                    ),
                    BillSectionResourceItemAttribute(
                        iconRes = Res.drawable.ic_light_bulb,
                        label = "City Power",
                        description = "Due: Nov 02",
                        price = "$78.20",
                        iconBackgroundColor = Color.Yellow,
                    ),
                ),
                favoriteList = listOf(
                    FavoriteSectionResourceItemAttribute(
                        imageRes = Res.drawable.img_mom_portrait,
                        label = "Mom",
                        description = "Last transfer: $50.00",
                    ),
                    FavoriteSectionResourceItemAttribute(
                        imageRes = Res.drawable.img_landlord_portrait,
                        label = "Landlord",
                        description = "Last transfer: $1200.00",
                    ),
                ),
            ),
        )
    }

    entry<NotificationScreenDestination> {
        NotificationScreen(
            state = NotificationScreenState(
                notificationList = listOf(
                    NotificationSectionAttribute(
                        date = "TODAY",
                        itemList = listOf(
                            NotificationResourceItemAttribute(
                                iconRes = Res.drawable.ic_card_outline,
                                label = "Card Purchase",
                                description = "$50.00 at Starbucks",
                                timestamp = "10:45 AM",
                            ),
                            NotificationResourceItemAttribute(
                                iconRes = Res.drawable.ic_card_outline,
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
                                iconRes = Res.drawable.ic_card_outline,
                                label = "New Cashback Offer",
                                description = "Earn 5% back on dining.",
                                timestamp = "4:15 PM",
                            ),
                            NotificationResourceItemAttribute(
                                iconRes = Res.drawable.ic_card_outline,
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
                                iconRes = Res.drawable.ic_card_outline,
                                label = "Card Purchase",
                                description = "$12.50 at Starbucks",
                                timestamp = "11:55 AM",
                                isUnread = false,
                            ),
                        ),
                    ),
                ),
            ),
        )
    }
}