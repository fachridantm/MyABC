package com.outivox.myabc.ui.presentation.dashboard.bottomsheet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.rememberNavBackStack
import com.outivox.myabc.core.bottomsheet.BottomSheetFlowContent
import com.outivox.myabc.core.util.LocalBottomSheetNavBackStack
import com.outivox.myabc.core.util.getOrNewViewModelStoreOwner
import com.outivox.myabc.ui.presentation.dashboard.DashboardViewModel
import com.outivox.myabc.ui.presentation.dashboard.bottomsheet.navigation.NavRoute
import com.outivox.myabc.ui.presentation.dashboard.bottomsheet.route.FirstRoute
import com.outivox.myabc.ui.presentation.dashboard.bottomsheet.route.SecondRoute

@Composable
fun BottomSheetDashboardContainer(
    showBottomSheet: Boolean = false,
    onCancel: () -> Unit,
) {
    if (!showBottomSheet) return
    CompositionLocalProvider(LocalBottomSheetNavBackStack provides rememberNavBackStack(NavRoute.FirstRoute)) {
        SampleBottomSheetFlow(
            onCancel = onCancel
        )
    }
}

@Composable
private fun SampleBottomSheetFlow(
    onCancel: () -> Unit = {},
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
        )
    }
    BottomSheetFlowContent(
        routes = sampleRoutes,
        onCancel = onCancel
    )
}