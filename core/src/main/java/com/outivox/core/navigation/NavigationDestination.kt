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
@Serializable
abstract class NavigationDestination

@Serializable
abstract class LaunchableDestination : NavigationDestination()