package com.outivox.myabc.core.util

import androidx.navigation.NavHostController
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.outivox.myabc.core.navigation.NavigationDestination

/**
 * Takes the last part of a dot-separated string, typically used to extract
 * the route name from a fully qualified route string.
 *
 * For example:
 * - "com.example.app.MyScreen", becomes "MyScreen".
 * - "LaunchableScreee/{data}" becomes "LaunchableScreee/{data}".>
 *
 * If the string is null, empty, or does not contain a dot, it will return null
 * or the original string if no dot is present.
 *
 * @return The extracted route name, or null if the input is null or empty.
 */
fun String?.takeRouteName(): String? = this?.split(".")?.lastOrNull()

/**
 * Extracts the screen name from a URL-like string.
 * It takes the part of the string before the first '/' and then before the first '?'.
 *
 * For example:
 * - "screenName/somePath?param=value" becomes "screenName"
 * - "screenName?param=value" becomes "screenName"
 * - "screenName" remains "screenName"
 * - null returns null
 * - "/path" returns "" (empty string before the first slash)
 * - "?query" returns "" (empty string before the first question mark)
 *
 * @return The extracted screen name, or null if the input string is null.
 *         Returns an empty string if the relevant part before '/' or '?' is empty.
 */
fun String?.takeScreenName(): String? = this?.split("/")?.firstOrNull()?.split("?")?.firstOrNull()

/**
 * Extension function to check if the previous destination is the same as the given destination class
 * Usage: navController.isPreviousDestinationSame<SomeDestination>()
 */
inline fun <reified T : NavigationDestination> NavHostController.checkPreviousDestination(): Boolean {
    val previousDestinationRoute = previousBackStackEntry?.destination?.route
    println("Previous destination route: $previousDestinationRoute")
    return previousDestinationRoute == T::class.qualifiedName
}

inline fun <reified T : NavigationDestination> NavHostController.checkCurrentDestination(): Boolean {
    val currentDestinationRoute = currentBackStackEntry?.destination?.route
    println("Current destination route: $currentDestinationRoute")
    return currentDestinationRoute == T::class.qualifiedName
}

fun NavHostController.checkPreviousDestionationIsNull(): Boolean {
    val previousDestinationRoute = previousBackStackEntry?.destination?.route
    println("Previous destination route: $previousDestinationRoute")
    return previousDestinationRoute == null
}

/**
 * Checks if the previous destination in the navigation back stack matches a specific destination class.
 *
 * This extension function allows you to verify the type of the second-to-last
 * entry in the `NavBackStack`. This is useful for implementing conditional navigation
 * logic based on the user's navigation history.
 *
 * @param destination A `KClass` reference to the destination class to check against.
 * @return `true` if the previous destination's class matches the provided `destination` class,
 *         `false` otherwise. Returns `false` if there is no previous destination.
 *
 * Usage:
 * ```
 * val isPreviousScreenMyScreen = navBackStack.checkPreviousDestination(MyScreen::class)
 * if (isPreviousScreenMyScreen) {
 *     // Handle logic specific to navigating from MyScreen
 * }
 * ```
 */
fun NavBackStack<NavKey>.checkPreviousDestination(destination: NavKey): Boolean {
    val previousDestination = this.getOrNull(this.size - 2)
    println("Previous destination: $previousDestination")
    return previousDestination == destination
}

fun NavBackStack<NavKey>.checkCurrentDestination(destination: NavKey): Boolean {
    val currentDestination = this.getOrNull(this.size - 1)
    println("Current destination: $currentDestination")
    return currentDestination == destination
}

fun NavBackStack<NavKey>.checkPreviousDestionationIsNull(): Boolean {
    val previousDestination = this.getOrNull(this.size - 2)
    println("Previous destination: $previousDestination")
    return previousDestination == null
}
