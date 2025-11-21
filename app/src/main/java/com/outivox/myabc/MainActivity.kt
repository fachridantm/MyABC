package com.outivox.myabc

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import com.outivox.core.deeplink.DeeplinkHandler
import com.outivox.core.deeplink.DeeplinkStartState
import com.outivox.core.navigation.NavigationViewModel
import com.outivox.core.navigation.SplashScreenDestination
import com.outivox.core.theme.MyABCTheme
import com.outivox.core.util.JsonExtensions.toJson
import com.outivox.core.util.LocalNavBackStack
import com.outivox.myabc.navigation.AppNavigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navBackStack: NavBackStack<NavKey>

    private val navigationViewModel by viewModels<NavigationViewModel>()

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.i(this::class.java.simpleName, "onNewIntent: ${intent.data.toJson()}")
        setIntent(intent)

        intent.data?.let { uri ->
            lifecycleScope.launch {
                if (::navBackStack.isInitialized) {
                    DeeplinkHandler.handleDeeplink(
                        uri = uri,
                        startState = DeeplinkStartState.WARM_HOT_START,
                        navBackStack = navBackStack,
                        navigationViewModel = navigationViewModel,
                        isLoggedIn = true, // TODO: Check if the user is logged in actually
                    )
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            navBackStack = rememberNavBackStack(SplashScreenDestination)
            splashScreen.setKeepOnScreenCondition { navBackStack.last() is SplashScreenDestination }

            MyABCTheme {
                CompositionLocalProvider(
                    LocalNavBackStack provides navBackStack,
                ) {
                    val deeplinkStartState = if (savedInstanceState == null) {
                        DeeplinkStartState.COLD_START
                    } else {
                        DeeplinkStartState.WARM_HOT_START
                    }

                    LaunchedEffect(Unit) {
                        intent.data?.let { uri ->
                            DeeplinkHandler.handleDeeplink(
                                uri = uri,
                                startState = deeplinkStartState,
                                navBackStack = navBackStack,
                                navigationViewModel = navigationViewModel,
                                isLoggedIn = true, // TODO: Check if the user is logged in actually
                            )
                        }
                    }

                    AppNavigator()
                }
            }
        }
    }
}
