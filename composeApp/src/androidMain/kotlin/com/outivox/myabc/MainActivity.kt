package com.outivox.myabc

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import com.outivox.myabc.core.deeplink.DeeplinkHandler
import com.outivox.myabc.core.deeplink.DeeplinkStartState
import com.outivox.myabc.core.navigation.NavigationViewModel
import com.outivox.myabc.core.navigation.SplashScreenDestination
import com.outivox.myabc.core.theme.MyABCTheme
import com.outivox.myabc.core.util.JsonExtensions.toJson
import com.outivox.myabc.core.util.LocalNavBackStack
import com.outivox.myabc.navigation.AppNavigator
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private lateinit var navBackStack: NavBackStack<NavKey>
    private val navigationViewModel by viewModel<NavigationViewModel>()

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
                    AppNavigator()

                    val deeplinkStartState = if (savedInstanceState == null) {
                        DeeplinkStartState.COLD_START
                    } else {
                        DeeplinkStartState.WARM_HOT_START
                    }

                    LaunchedEffect(Unit) {
                        handleDeeplink(intent, deeplinkStartState)
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.i(this::class.java.simpleName, "onNewIntent: ${intent.data.toJson()}")
        setIntent(intent)
        handleDeeplink(intent, DeeplinkStartState.WARM_HOT_START)
    }

    private fun handleDeeplink(intent: Intent?, startState: DeeplinkStartState) {
        intent?.data?.let { uri ->
            lifecycleScope.launch {
                if (::navBackStack.isInitialized) {
                    DeeplinkHandler.handleDeeplink(
                        uri = uri,
                        startState = startState,
                        navBackStack = navBackStack,
                        navigationViewModel = navigationViewModel,
                        isLoggedIn = true, // TODO: Check if the user is logged in actually
                    )
                }
            }
        }
    }
}