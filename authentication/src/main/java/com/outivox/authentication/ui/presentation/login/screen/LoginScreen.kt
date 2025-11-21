package com.outivox.authentication.ui.presentation.login.screen

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.outivox.authentication.R
import com.outivox.core.deeplink.DeeplinkHandler
import com.outivox.core.deeplink.DeeplinkStartState
import com.outivox.core.navigation.NavigationViewModel
import com.outivox.core.theme.MyABCTheme
import com.outivox.core.util.LocalNavBackStack
import com.outivox.core.util.LocalePreview
import com.outivox.core.util.checkPreviousDestionationIsNull
import com.outivox.core.util.getOrNewViewModelStoreOwner

private const val TAG = "LoginScreen"

@Composable
fun LoginScreen(
    navigationViewModel: NavigationViewModel = getOrNewViewModelStoreOwner(),
) {
    val activity = LocalActivity.current
    val navBackStack = LocalNavBackStack.current

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
    BackHandler {
        if (navBackStack.checkPreviousDestionationIsNull()) {
            activity?.finishAndRemoveTask()
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
                Text(stringResource(R.string.auth_action_login))
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