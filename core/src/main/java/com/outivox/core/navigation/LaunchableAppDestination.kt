package com.outivox.core.navigation

import kotlinx.serialization.Serializable

// TODO 5: Add navigation destinations for the app and separate them into inter and inner modules

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