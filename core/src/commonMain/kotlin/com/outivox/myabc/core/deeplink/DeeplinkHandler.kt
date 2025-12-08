package com.outivox.myabc.core.deeplink

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.outivox.myabc.core.navigation.NavigationDestination
import com.outivox.myabc.core.navigation.NavigationViewModel
import com.outivox.myabc.core.util.PrintLog
import com.outivox.myabc.core.util.getQueryParameterBooleanOrNull
import com.outivox.myabc.core.util.toPlatformUri

object DeeplinkHandler {
    private const val TAG = "DeeplinkHandler"

    /**
     * Determines the access level of a deeplink based on its URI.
     *
     * This function analyzes a given [android.net.Uri] to determine whether it represents a
     * valid deeplink and, if so, whether it requires pre-login or post-login
     * access. The access level is determined based on the presence and value of the
     * "auth" query parameter and whether the URI string satisfies certain
     * validation criteria (defined by `isPreLoginLevelValid()` and
     * `isPostLoginLevelValid()` extension functions).
     *
     * @param uri The URI of the deeplink to analyze. Can be null.
     * @return A [DeeplinkAccessLevel] representing the determined access level:
     *         - [DeeplinkAccessLevel.INVALID]: If the URI is null, or if it is not a valid pre-login or post-login deeplink.
     *         - [DeeplinkAccessLevel.PRE_LOGIN]: If the URI is a valid pre-login deeplink, either because the "auth" parameter is false, or because it's absent and the URI string passes the pre-login validation.
     *         - [DeeplinkAccessLevel.POST_LOGIN]: If the URI is a valid post-login deeplink, either because the "auth" parameter is true, or because it's absent and the URI string passes the post-login validation.
     *
     * The logic flow is as follows:
     * 1. If the URI is null, return [DeeplinkAccessLevel.INVALID].
     * 2. Check for the "auth" query parameter.
     *   a. If "auth" is present and true, check if the URI string is a valid post-login deeplink. If so, return [DeeplinkAccessLevel.POST_LOGIN]; otherwise, return [DeeplinkAccessLevel.INVALID].
     *   b. If "auth" is present and false, check if the URI string is a valid pre-login deeplink. If so, return [DeeplinkAccessLevel.PRE_LOGIN]; otherwise, return [DeeplinkAccessLevel.INVALID].
     * 3. If "auth" is not present, check if the URI string is a valid pre-login deeplink, if so return [DeeplinkAccessLevel.PRE_LOGIN]
     */
    private fun getDeeplinkAccessLevel(uri: String?): DeeplinkAccessLevel {
        if (uri == null) return DeeplinkAccessLevel.INVALID
        val platformUri = uri.toPlatformUri()
        val auth = platformUri?.getQueryParameterBooleanOrNull("auth")
        PrintLog.i(TAG, "Deeplink auth: $auth")

        if (auth != null) {
            return if (auth) {
                if (uri.isPostLoginLevelValid()) {
                    DeeplinkAccessLevel.POST_LOGIN
                } else {
                    DeeplinkAccessLevel.INVALID
                }
            } else {
                if (uri.isPreLoginLevelValid()) {
                    DeeplinkAccessLevel.PRE_LOGIN
                } else {
                    DeeplinkAccessLevel.INVALID
                }
            }
        } else {
            return when {
                uri.isPreLoginLevelValid() -> {
                    DeeplinkAccessLevel.PRE_LOGIN
                }

                uri.isPostLoginLevelValid() -> {
                    DeeplinkAccessLevel.POST_LOGIN
                }

                else -> {
                    DeeplinkAccessLevel.INVALID
                }
            }
        }
    }

    private fun getDeeplinkViewLevel(destination: NavigationDestination?): DeeplinkViewLevel {
        val bottomSheetDestinations = listOf<NavigationDestination>()
        return if (destination in bottomSheetDestinations) DeeplinkViewLevel.BOTTOM_SHEET else DeeplinkViewLevel.SCREEN
    }

    private fun mapDeeplink(uri: String): Deeplink {
        val accessLevel = getDeeplinkAccessLevel(uri)
        val destination = when (accessLevel) {
            DeeplinkAccessLevel.PRE_LOGIN -> {
                uri.toPreLoginDestination()
            }

            DeeplinkAccessLevel.POST_LOGIN -> {
                uri.toPostLoginDestination()
            }

            DeeplinkAccessLevel.INVALID -> {
                null
            }
        }
        val viewLevel = getDeeplinkViewLevel(destination)

        return Deeplink(
            uri = uri,
            destination = destination,
            accessLevel = accessLevel,
            viewLevel = viewLevel
        )
    }

    private fun handleDeeplinkNavigation(
        deeplink: Deeplink,
        destination: NavigationDestination,
        navController: NavBackStack<NavKey>,
        navigationViewModel: NavigationViewModel,
    ) {
        when (deeplink.viewLevel) {
            DeeplinkViewLevel.BOTTOM_SHEET -> {
                // Save the deeplink first, so that it can be processed as a state of the bottom sheet.
                navigationViewModel.saveDeeplink(deeplink)
            }

            DeeplinkViewLevel.SCREEN -> {
                runCatching {
                    navController.apply {
                        clear()
                        add(destination)
                    }
                }.onFailure {
                    PrintLog.e(TAG, it.message.orEmpty(), it)
                }.onSuccess {
                    navigationViewModel.resetDeeplink()
                }
            }
        }
    }

    fun handleDeeplink(
        uri: String,
        isLoggedIn: Boolean,
        startState: DeeplinkStartState,
        navBackStack: NavBackStack<NavKey>,
        navigationViewModel: NavigationViewModel,
    ) {
        val deeplink = mapDeeplink(uri)
        PrintLog.i(TAG, "Deeplink: $deeplink")
        PrintLog.i(TAG, "Start state: ${startState.name}")

        deeplink.destination?.let { destination ->
            when (startState) {
                DeeplinkStartState.COLD_START -> {
                    // Safe the deeplink first, so that it can be processed after splash screen.
                    navigationViewModel.saveDeeplink(deeplink)
                }

                DeeplinkStartState.WARM_HOT_START -> {
                    when (deeplink.accessLevel) {
                        DeeplinkAccessLevel.POST_LOGIN -> {
                            if (isLoggedIn) {
                                handleDeeplinkNavigation(deeplink, destination, navBackStack, navigationViewModel)
                            } else {
                                // Save the deeplink first, so that it can be processed after login.
                                navigationViewModel.saveDeeplink(deeplink)
                            }
                        }

                        DeeplinkAccessLevel.PRE_LOGIN -> {
                            handleDeeplinkNavigation(deeplink, destination, navBackStack, navigationViewModel)
                        }

                        DeeplinkAccessLevel.INVALID -> {
                            PrintLog.e(TAG, "Invalid deeplink")
                        }
                    }
                }
            }
        }
    }
}