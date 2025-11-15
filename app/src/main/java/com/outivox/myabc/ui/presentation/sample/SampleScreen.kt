package com.outivox.myabc.ui.presentation.sample

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.outivox.core.navigation.LaunchableLoginScreenDestination
import com.outivox.core.theme.MyABCTheme
import com.outivox.core.util.LocalNavController
import com.outivox.core.util.checkPreviousDestination
import com.outivox.core.util.checkPreviousDestionationIsNull

private const val TAG = "SampleScreen"

@Composable
fun SampleScreen() {
    val activity = LocalActivity.current
    val navController = LocalNavController.current
    SampleScreenContent()
    BackHandler {
        if (navController.checkPreviousDestination<LaunchableLoginScreenDestination>() || navController.checkPreviousDestionationIsNull()) {
            activity?.finishAndRemoveTask()
        } else {
            navController.popBackStack()
        }
    }
}

@Composable
private fun SampleScreenContent() {
    Scaffold { innerPadding ->
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