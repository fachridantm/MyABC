package com.outivox.myabc.ui.presentation.splash

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.outivox.core.deeplink.DeeplinkAccessLevel
import com.outivox.core.navigation.DashboardScreenDestination
import com.outivox.core.navigation.LaunchableLoginScreenDestination
import com.outivox.core.navigation.NavigationViewModel
import com.outivox.core.util.JsonExtensions.toJson
import com.outivox.core.util.LocalNavBackStack
import com.outivox.core.util.data
import com.outivox.core.util.getOrNewViewModelStoreOwner
import com.outivox.core.util.isSuccess

private const val TAG = "SplashScreen"

@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel = hiltViewModel(),
    navigationViewModel: NavigationViewModel = getOrNewViewModelStoreOwner(),
) {
    val initState by splashViewModel.initState.collectAsStateWithLifecycle()
    val deeplink by navigationViewModel.deeplink.collectAsStateWithLifecycle()

    val navBackStack = LocalNavBackStack.current
    val context = LocalContext.current

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
                                    Toast.makeText(context, "Failed to navigate", Toast.LENGTH_SHORT).show()
                                    Log.e(TAG, it.message.orEmpty(), it)
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
                                Toast.makeText(context, "Failed to navigate", Toast.LENGTH_SHORT).show()
                                Log.e(TAG, it.message.orEmpty(), it)
                            }
                        }

                        DeeplinkAccessLevel.INVALID -> {
                            Log.e(TAG, "Invalid deeplink access level: $accessLevel")
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
                    Toast.makeText(context, "Failed to navigate", Toast.LENGTH_SHORT).show()
                    Log.e(TAG, it.message.orEmpty(), it)
                }
            }
            splashViewModel.resetInitState()
        }
    }
}