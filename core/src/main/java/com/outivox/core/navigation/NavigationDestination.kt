package com.outivox.core.navigation

import kotlinx.serialization.Serializable

/**
 * Base class for all destinations in the app
 *
 * NavigationDestination is used for inner module navigation (within the module)
 * LaunchableDestination is used for inter module navigation (between modules)
 *
 * @sample com.outivox.myabc.navigation.SplashScreenDestination
 */
// TODO (Step 2.1): In Navigation 3, all destinations must implement androidx.navigation3.runtime.NavKey.
// 1. Import androidx.navigation3.runtime.NavKey
// 2. Change `abstract class NavigationDestination` to `abstract class NavigationDestination : NavKey`
@Serializable
abstract class NavigationDestination

@Serializable
abstract class LaunchableDestination : NavigationDestination()