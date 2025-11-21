package com.outivox.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * Base class for all destinations in the app
 *
 * NavigationDestination is used for inner module navigation (within the module)
 * LaunchableDestination is used for inter module navigation (between modules)
 *
 * @sample com.outivox.myabc.navigation.SplashScreenDestination
 */
@Serializable
abstract class NavigationDestination : NavKey

@Serializable
abstract class LaunchableDestination : NavigationDestination()