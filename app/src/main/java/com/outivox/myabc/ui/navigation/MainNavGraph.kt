package com.outivox.myabc.ui.navigation

import androidx.compose.ui.graphics.Color
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.outivox.core.navigation.DashboardScreenDestination
import com.outivox.core.navigation.LaunchableLoginScreenDestination
import com.outivox.core.navigation.LaunchableSampleScreenDestination
import com.outivox.core.navigation.NotificationScreenDestination
import com.outivox.core.navigation.SettingDetailScreenDestination
import com.outivox.core.navigation.SettingScreenDestination
import com.outivox.core.navigation.SplashScreenDestination
import com.outivox.core.navigation.TransactionScreenDestination
import com.outivox.core.util.LocalNavBackStack
import com.outivox.myabc.R
import com.outivox.myabc.ui.molecules.ChipAttribute
import com.outivox.myabc.ui.molecules.MenuCardAttribute
import com.outivox.myabc.ui.organisms.BillSectionResourceItemAttribute
import com.outivox.myabc.ui.organisms.FavoriteSectionResourceItemAttribute
import com.outivox.myabc.ui.organisms.MenuResourceItemAttribute
import com.outivox.myabc.ui.organisms.NotificationResourceItemAttribute
import com.outivox.myabc.ui.organisms.NotificationSectionAttribute
import com.outivox.myabc.ui.organisms.SettingMenuSectionAttribute
import com.outivox.myabc.ui.organisms.TransactionHistorySectionAttribute
import com.outivox.myabc.ui.organisms.TransactionResourceItemAttribute
import com.outivox.myabc.ui.presentation.dashboard.DashboardScreen
import com.outivox.myabc.ui.presentation.dashboard.DashboardScreenState
import com.outivox.myabc.ui.presentation.notification.NotificationScreen
import com.outivox.myabc.ui.presentation.notification.NotificationScreenState
import com.outivox.myabc.ui.presentation.sample.SampleScreen
import com.outivox.myabc.ui.presentation.setting.SettingScreen
import com.outivox.myabc.ui.presentation.setting.SettingScreenEvent
import com.outivox.myabc.ui.presentation.setting.SettingScreenState
import com.outivox.myabc.ui.presentation.setting.detail.SettingDetailScreen
import com.outivox.myabc.ui.presentation.splash.SplashScreen
import com.outivox.myabc.ui.presentation.transaction.TransactionScreen
import com.outivox.myabc.ui.presentation.transaction.TransactionScreenEvent
import com.outivox.myabc.ui.presentation.transaction.TransactionScreenState

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

    entry<NotificationScreenDestination> {
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
        )
    }

    // =========================================================================
    // TODO (Step 5.1): Register inner-module TransactionScreenDestination
    // In Navigation 3, destinations implementing NavKey are registered via EntryProviderScope.entry<T>()
    // =========================================================================
    entry<TransactionScreenDestination> {
        val navBackStack = LocalNavBackStack.current
        TransactionScreen(
            state = TransactionScreenState(
                accountNumber = "**** 1234",
                accountBalance = "$1,234.56",
                chipList = listOf(
                    ChipAttribute(
                        leadingIconRes = R.drawable.ic_date_picker,
                        label = "Last 30 days",
                        trailingIconRes = R.drawable.ic_chevron_down,
                    ),
                    ChipAttribute(label = "All", isSelected = true),
                    ChipAttribute(label = "Income"),
                    ChipAttribute(label = "Expenses"),
                ),
                transactionList = listOf(
                    TransactionHistorySectionAttribute(
                        date = "TODAY",
                        itemList = listOf(
                            TransactionResourceItemAttribute(
                                iconRes = R.drawable.ic_cup_outline,
                                label = "Amazon.com",
                                description = "Online Purchase",
                                price = "$78.50",
                            ),
                            TransactionResourceItemAttribute(
                                iconRes = R.drawable.ic_cup_outline,
                                label = "Starbucks",
                                description = "Coffee",
                                price = "$5.75",
                            ),
                        ),
                    ),
                    TransactionHistorySectionAttribute(
                        date = "YESTERDAY",
                        itemList = listOf(
                            TransactionResourceItemAttribute(
                                iconRes = R.drawable.ic_cup_outline,
                                label = "Salary Deposit",
                                description = "Direct Deposit",
                                price = "$2,500.00",
                                isCredit = true,
                            ),
                        ),
                    ),
                ),
            ),
            event = { screenEvent ->
                when (screenEvent) {
                    TransactionScreenEvent.OnBackPressed -> {
                        // TODO (Step 5.4): Handle inner-module back navigation using navBackStack.removeLastOrNull()
                        navBackStack.removeLastOrNull()
                    }
                    is TransactionScreenEvent.OnChipClicked -> {}
                    TransactionScreenEvent.OnSearchClicked -> {}
                }
            }
        )
    }

    // =========================================================================
    // TODO (Step 5.1): Register inner-module SettingScreenDestination
    // TODO (Step 5.3): Navigate from SettingScreen menu item to SettingDetailScreenDestination
    // TODO (Step 6.2): Navigate inter-module to LaunchableLoginScreenDestination on logout
    // =========================================================================
    entry<SettingScreenDestination> {
        val navBackStack = LocalNavBackStack.current
        SettingScreen(
            state = SettingScreenState(
                userAvatar = R.drawable.img_man_side_view,
                userName = "Alex Doe",
                userEmail = "alex.doe@email.com",
                settingMenuList = listOf(
                    SettingMenuSectionAttribute(
                        title = "PERSONAL INFORMATION",
                        itemList = listOf(
                            MenuResourceItemAttribute(
                                iconRes = R.drawable.ic_setting,
                                label = "Contact Details",
                                description = "Manage your phone and email",
                            ),
                            MenuResourceItemAttribute(
                                iconRes = R.drawable.ic_setting,
                                label = "Mailing Address",
                            ),
                        ),
                    ),
                    SettingMenuSectionAttribute(
                        title = "SECURITY",
                        itemList = listOf(
                            MenuResourceItemAttribute(
                                iconRes = R.drawable.ic_setting,
                                label = "Change Password",
                            ),
                            MenuResourceItemAttribute(
                                iconRes = R.drawable.ic_setting,
                                label = "Manage Biometrics",
                            ),
                            MenuResourceItemAttribute(
                                iconRes = R.drawable.ic_setting,
                                label = "Linked Accounts & Devices",
                            ),
                        ),
                    ),
                    SettingMenuSectionAttribute(
                        title = "APP PREFERENCES",
                        itemList = listOf(
                            MenuResourceItemAttribute(
                                iconRes = R.drawable.ic_setting,
                                label = "Notification",
                            ),
                            MenuResourceItemAttribute(
                                iconRes = R.drawable.ic_setting,
                                label = "Theme",
                            ),
                        ),
                    ),
                ),
            ),
            event = { screenEvent ->
                when (screenEvent) {
                    SettingScreenEvent.OnBackPressed -> {
                        navBackStack.removeLastOrNull()
                    }
                    SettingScreenEvent.OnLogoutClicked -> {
                        // Inter-module navigation from :app to :authentication
                        navBackStack.apply {
                            clear()
                            add(LaunchableLoginScreenDestination)
                        }
                    }
                    is SettingScreenEvent.OnSettingItemClicked -> {
                        // Inner-module navigation to detail screen
                        navBackStack.add(SettingDetailScreenDestination(settingTitle = screenEvent.title))
                    }
                }
            }
        )
    }

    // =========================================================================
    // TODO (Step 5.1 & Step 7.4): Register SettingDetailScreenDestination entry
    // Reads typed argument from SettingDetailScreenDestination and passes to SettingDetailScreen
    // =========================================================================
    entry<SettingDetailScreenDestination> { destination ->
        val navBackStack = LocalNavBackStack.current
        SettingDetailScreen(
            settingTitle = destination.settingTitle,
            onBackPressed = {
                navBackStack.removeLastOrNull()
            },
        )
    }
}