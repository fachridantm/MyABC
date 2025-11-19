package com.outivox.myabc.ui.presentation.dashboard.bottomsheet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.outivox.core.bottomsheet.BottomSheetFlow
import com.outivox.core.bottomsheet.BottomSheetFlowContent
import com.outivox.core.bottomsheet.rememberBottomSheetFlow
import com.outivox.core.util.LocalBottomSheetNavController
import com.outivox.core.util.getOrNewViewModelStoreOwner
import com.outivox.myabc.ui.presentation.dashboard.DashboardViewModel
import com.outivox.myabc.ui.presentation.dashboard.bottomsheet.route.FirstRoute
import com.outivox.myabc.ui.presentation.dashboard.bottomsheet.route.SecondRoute

@Composable
fun BottomSheetDashboardContainer(
    bottomSheetFlow: BottomSheetFlow= rememberBottomSheetFlow(),
    showBottomSheet: Boolean = false,
    onCancel: () -> Unit
) {
    if (!showBottomSheet) return
    CompositionLocalProvider(LocalBottomSheetNavController provides rememberNavController()) {
        SampleBottomSheetFlow(
            bottomSheetFlow = bottomSheetFlow,
            onCancel = onCancel
        )
    }
}

@Composable
private fun SampleBottomSheetFlow(
    onCancel: () -> Unit = {},
    bottomSheetFlow: BottomSheetFlow = rememberBottomSheetFlow(),
    dashboardViewModel: DashboardViewModel = getOrNewViewModelStoreOwner(),
) {
    val sampleRoutes = remember {
        listOf(
            FirstRoute(
                onCancel = onCancel,
            ),
            SecondRoute(
                onCancel = onCancel,
            )
        )
    }.map { route ->
        route.createRoute(
            onCancel = onCancel,
            viewModel = dashboardViewModel,
            bottomSheetFlow = bottomSheetFlow
        )
    }
    BottomSheetFlowContent(
        bottomSheetFlow = bottomSheetFlow,
        routes = sampleRoutes,
        onCancel = onCancel
    )
}