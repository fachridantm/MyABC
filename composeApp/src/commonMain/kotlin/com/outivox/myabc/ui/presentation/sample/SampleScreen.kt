package com.outivox.myabc.ui.presentation.sample

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.outivox.myabc.core.navigation.LaunchableLoginScreenDestination
import com.outivox.myabc.core.theme.MyABCTheme
import com.outivox.myabc.core.util.LocalNavBackStack
import com.outivox.myabc.core.util.checkPreviousDestination
import com.outivox.myabc.core.util.checkPreviousDestionationIsNull

private const val TAG = "SampleScreen"

@Composable
fun SampleScreen() {
    val activity = LocalActivity.current
    val navBackStack = LocalNavBackStack.current
    SampleScreenContent()
    BackHandler {
        if (navBackStack.checkPreviousDestination(LaunchableLoginScreenDestination) || navBackStack.checkPreviousDestionationIsNull()) {
            activity?.finishAndRemoveTask()
        } else {
            navBackStack.removeLastOrNull()
        }
    }
}

@Composable
private fun SampleScreenContent() {
    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        containerColor = Color.White,
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Sample Screen")
        }
    }
}

@Preview
@Composable
private fun Preview() {
    MyABCTheme {
        SampleScreenContent()
    }
}