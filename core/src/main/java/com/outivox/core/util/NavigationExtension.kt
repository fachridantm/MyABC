package com.outivox.core.util

import androidx.navigation.NavHostController
import com.outivox.core.navigation.NavigationDestination

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
    println("Previous destination: ${previousBackStackEntry?.destination?.route}")
    return previousBackStackEntry?.destination?.route == T::class.qualifiedName
}

inline fun <reified T : NavigationDestination> NavHostController.checkCurrentDestination(): Boolean {
    println("Current destination: ${currentBackStackEntry?.destination?.route}")
    return currentBackStackEntry?.destination?.route == T::class.qualifiedName
}

fun NavHostController.checkPreviousDestionationIsNull(): Boolean {
    return previousBackStackEntry?.destination?.route == null
}
