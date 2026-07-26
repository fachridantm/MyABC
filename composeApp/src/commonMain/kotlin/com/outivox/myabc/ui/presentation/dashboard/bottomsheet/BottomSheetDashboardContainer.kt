package com.outivox.myabc.ui.presentation.dashboard.bottomsheet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.savedstate.serialization.SavedStateConfiguration
import com.outivox.myabc.core.bottomsheet.BottomSheetFlowContent
import com.outivox.myabc.core.util.LocalBottomSheetNavBackStack
import com.outivox.myabc.ui.presentation.dashboard.DashboardViewModel
import com.outivox.myabc.ui.presentation.dashboard.bottomsheet.navigation.NavRoute
import com.outivox.myabc.ui.presentation.dashboard.bottomsheet.route.FirstRoute
import com.outivox.myabc.ui.presentation.dashboard.bottomsheet.route.FourthRoute
import com.outivox.myabc.ui.presentation.dashboard.bottomsheet.route.SecondRoute
import com.outivox.myabc.ui.presentation.dashboard.bottomsheet.route.ThirdRoute
import com.outivox.myabc.util.navKeySerializer
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BottomSheetDashboardContainer(
    showBottomSheet: Boolean = false,
    onCancel: () -> Unit,
) {
    if (!showBottomSheet) return
    val navBackStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = navKeySerializer
        },
        NavRoute.FirstRoute
    )
    CompositionLocalProvider(LocalBottomSheetNavBackStack provides navBackStack) {
        SampleBottomSheetFlow(
            onCancel = onCancel,
        )
    }
}

@Composable
private fun SampleBottomSheetFlow(
    onCancel: () -> Unit = {},
    dashboardViewModel: DashboardViewModel = koinViewModel(),
) {
    val sampleRoutes = remember {
        listOf(
            FirstRoute(
                onCancel = onCancel,
            ),
            SecondRoute(
                onCancel = onCancel,
            ),
            ThirdRoute(
                onCancel = onCancel,
            ),
            FourthRoute(
                onCancel = onCancel,
            ),
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