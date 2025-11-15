package com.outivox.myabc.ui.presentation.dashboard

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.outivox.core.bottomsheet.rememberBottomSheetFlow
import com.outivox.core.theme.MyABCTheme
import com.outivox.myabc.ui.presentation.dashboard.bottomsheet.BottomSheetDashboardContainer
import com.outivox.myabc.ui.presentation.dashboard.bottomsheet.navigation.NavRoute

@Composable
fun DashboardScreen(data: String) {
    val activity = LocalActivity.current

    var showBottomSheet by remember { mutableStateOf(false) }

    DashboardScreenContent(
        data = data,
        onClick = { showBottomSheet = true }
    )
    // TODO 16: Create a BottomSheetFlowContainer and pass the bottomSheetFlow to it.
    BottomSheetDashboardContainer(
        bottomSheetFlow = rememberBottomSheetFlow(
            startDestination = NavRoute.FirstRoute.route
        ),
        showBottomSheet = showBottomSheet,
        onCancel = { showBottomSheet = false }
    )
    BackHandler {
        activity?.finishAndRemoveTask()
    }
}

@Composable
private fun DashboardScreenContent(data: String, onClick: () -> Unit = {}) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Dashboard Screen with data: $data")
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onClick
            ) {
                Text(text = "Start bottom sheet flow")
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    MyABCTheme {
        DashboardScreenContent(data = "Hello World")
    }
}