@file:OptIn(ExperimentalMaterial3Api::class)

package com.outivox.myabc.ui.presentation.dashboard.bottomsheet.route

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.outivox.core.bottomsheet.BaseBottomSheetRoute
import com.outivox.core.bottomsheet.BottomSheetFlow
import com.outivox.core.component.BottomSheetFooterState
import com.outivox.core.component.BottomSheetHeaderState
import com.outivox.core.navigation.LaunchableSampleScreenDestination
import com.outivox.core.theme.MyABCBottomSheetTheme
import com.outivox.core.util.LocalBottomSheetNavController
import com.outivox.core.util.LocalNavController
import com.outivox.myabc.ui.presentation.dashboard.DashboardViewModel
import com.outivox.myabc.ui.presentation.dashboard.bottomsheet.navigation.NavRoute

class SecondRoute (
    private val onCancel: () -> Unit = {},
) : BaseBottomSheetRoute<DashboardViewModel>() {
    @Composable
    override fun getNavRoute() = NavRoute.SecondRoute.route

    @Composable
    override fun getHeaderState(): BottomSheetHeaderState {
        val navController = LocalBottomSheetNavController.current
        return BottomSheetHeaderState(
            title = "Second route",
            leadingIcon = {
                IconButton(onClick = navController::popBackStack) {
                    Icon(
                        modifier = Modifier.requiredSize(24.dp),
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = onCancel) {
                    Icon(
                        modifier = Modifier.requiredSize(24.dp),
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                    )
                }
            }
        )
    }

    @Composable
    override fun getFooterState(): BottomSheetFooterState {
        val context = LocalContext.current
        val navController = LocalNavController.current
        return BottomSheetFooterState(
            textPrimary = "Next",
            onPrimaryClicked = {
//                Toast.makeText(context, "End of the route", Toast.LENGTH_SHORT).show()
                onCancel()
                navController.navigate(LaunchableSampleScreenDestination)
            }
        )
    }

    @Composable
    override fun getContent(
        bottomSheetFlow: BottomSheetFlow,
        viewModel: DashboardViewModel,
        navBackStackEntry: NavBackStackEntry,
        sheetState: SheetState,
        scrollState: ScrollState,
        onCancel: () -> Unit,
    ) {
        val navController = LocalBottomSheetNavController.current
        SecondContent()
        BackHandler(onBack = navController::popBackStack)
    }
}

@Composable
private fun SecondContent() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Second content")
    }
}

@Preview
@Composable
private fun Preview() {
    MyABCBottomSheetTheme(
        headerState = BottomSheetHeaderState(title = "Second Route"),
        footerState = BottomSheetFooterState(textPrimary = "Next")
    ) {
        SecondContent()
    }
}