package com.outivox.core.navigation

import kotlinx.serialization.Serializable

// Inter Module

@Serializable
data object  LaunchableSampleScreenDestination : LaunchableDestination()

@Serializable
data object LaunchableLoginScreenDestination : LaunchableDestination()

// Inner Module

@Serializable
data object SplashScreenDestination : NavigationDestination()

@Serializable
data class DashboardScreenDestination(val dataJson: String) : NavigationDestination()

@Serializable
data object NotificationScreenDestination : NavigationDestination()

@Serializable
data object TransactionScreenDestination : NavigationDestination()

@Serializable
data object SettingScreenDestination : NavigationDestination()