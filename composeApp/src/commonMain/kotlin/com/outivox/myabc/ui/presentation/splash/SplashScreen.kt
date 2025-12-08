package com.outivox.myabc.ui.presentation.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.outivox.myabc.core.deeplink.DeeplinkAccessLevel
import com.outivox.myabc.core.navigation.DashboardScreenDestination
import com.outivox.myabc.core.navigation.LaunchableLoginScreenDestination
import com.outivox.myabc.core.navigation.NavigationViewModel
import com.outivox.myabc.core.util.JsonExtensions.toJson
import com.outivox.myabc.core.util.LocalNavBackStack
import com.outivox.myabc.core.util.LocalToastManager
import com.outivox.myabc.core.util.PrintLog
import com.outivox.myabc.core.util.data
import com.outivox.myabc.core.util.isSuccess
import org.koin.compose.viewmodel.koinViewModel

private const val TAG = "SplashScreen"

@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel = koinViewModel(),
    navigationViewModel: NavigationViewModel = koinViewModel(),
) {
    val initState by splashViewModel.initState.collectAsStateWithLifecycle()
    val deeplink by navigationViewModel.deeplink.collectAsStateWithLifecycle()

    val navBackStack = LocalNavBackStack.current
    val toastManager = LocalToastManager.current

    LaunchedEffect(Unit) {
        splashViewModel.init()
    }

    LaunchedEffect(initState) {
        if (initState.isSuccess) {
            val data = initState.data ?: return@LaunchedEffect
            val dataJson = data.toJson()
            if (deeplink != null) {
                deeplink?.let { deeplinkData ->
                    when (val accessLevel = deeplinkData.accessLevel) {
                        DeeplinkAccessLevel.PRE_LOGIN -> {
                            // Navigate to the destination
                            deeplinkData.destination?.let { destination ->
                                runCatching {
                                    navBackStack.apply {
                                        clear()
                                        add(destination)
                                    }
                                }.onFailure {
                                    toastManager.showToast("Failed to navigate")
                                    PrintLog.e(TAG, it.message.orEmpty(), it)
                                }.onSuccess {
                                    navigationViewModel.resetDeeplink()
                                }
                            }
                        }

                        DeeplinkAccessLevel.POST_LOGIN -> {
                            // Navigate to login first, then to the destination.
                            runCatching {
                                navBackStack.apply {
                                    clear()
                                    add(LaunchableLoginScreenDestination)
                                }
                            }.onFailure {
                                toastManager.showToast("Failed to navigate")
                                PrintLog.e(TAG, it.message.orEmpty(), it)
                            }
                        }

                        DeeplinkAccessLevel.INVALID -> {
                            PrintLog.e(TAG, "Invalid deeplink access level: $accessLevel")
                        }
                    }
                }
            } else {
                runCatching {
                    navBackStack.apply {
                        clear()
                        add(DashboardScreenDestination(dataJson))
                    }
                }.onFailure {
                    toastManager.showToast("Failed to navigate")
                    PrintLog.e(TAG, it.message.orEmpty(), it)
                }
            }
            splashViewModel.resetInitState()
        }
    }
}