package com.outivox.myabc.authentication.presentation.login.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigationevent.NavigationEventInfo
import androidx.navigationevent.compose.NavigationBackHandler
import androidx.navigationevent.compose.rememberNavigationEventState
import com.outivox.myabc.authentication.generated.resources.Res
import com.outivox.myabc.authentication.generated.resources.auth_action_login
import com.outivox.myabc.core.deeplink.DeeplinkHandler
import com.outivox.myabc.core.deeplink.DeeplinkStartState
import com.outivox.myabc.core.navigation.NavigationViewModel
import com.outivox.myabc.core.theme.MyABCTheme
import com.outivox.myabc.core.util.LocalAppManager
import com.outivox.myabc.core.util.LocalNavBackStack
import com.outivox.myabc.core.util.LocalePreview
import com.outivox.myabc.core.util.checkPreviousDestionationIsNull
import org.jetbrains.compose.resources.stringResource

private const val TAG = "LoginScreen"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    navigationViewModel: NavigationViewModel = NavigationViewModel(),
) {
    val navBackStack = LocalNavBackStack.current
    val appManager = LocalAppManager.current

    val navigationEventState = rememberNavigationEventState(
        currentInfo = NavigationEventInfo.None
    )

    val deeplink by navigationViewModel.deeplink.collectAsStateWithLifecycle()

    LoginScreenContent(
        onLoginClicked = {
            deeplink?.uri?.let { uri ->
                DeeplinkHandler.handleDeeplink(
                    uri = uri,
                    startState = DeeplinkStartState.WARM_HOT_START,
                    navBackStack = navBackStack,
                    navigationViewModel = navigationViewModel,
                    isLoggedIn = true
                )
            }
        }
    )


    NavigationBackHandler(
        state = navigationEventState,
    ) {
        if (navBackStack.checkPreviousDestionationIsNull()) {
            appManager.exitApp()
        } else {
            navBackStack.removeLastOrNull()
        }
    }
}

@Composable
private fun LoginScreenContent(
    onLoginClicked: () -> Unit = {},
) {
    Scaffold(
        containerColor = Color.White,
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = onLoginClicked
            ) {
                Text(stringResource(Res.string.auth_action_login))
            }
        }
    }
}

@LocalePreview
@Composable
private fun Preview() {
    MyABCTheme {
        LoginScreenContent()
    }
}