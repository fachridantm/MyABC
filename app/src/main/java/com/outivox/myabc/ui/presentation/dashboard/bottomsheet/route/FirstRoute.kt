@file:OptIn(ExperimentalMaterial3Api::class)

package com.outivox.myabc.ui.presentation.dashboard.bottomsheet.route

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.icons.Icons
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
import androidx.navigation3.runtime.NavKey
import com.outivox.core.bottomsheet.BaseBottomSheetRoute
import com.outivox.core.component.BottomSheetFooterState
import com.outivox.core.component.BottomSheetHeaderState
import com.outivox.core.theme.MyABCBottomSheetTheme
import com.outivox.core.util.LocalBottomSheetNavBackStack
import com.outivox.myabc.ui.presentation.dashboard.DashboardViewModel
import com.outivox.myabc.ui.presentation.dashboard.bottomsheet.navigation.NavRoute

private const val TAG = "FirstRoute"

class FirstRoute(
    private val onCancel: () -> Unit = {},
) : BaseBottomSheetRoute<DashboardViewModel>() {
    @Composable
    override fun getNavRoute() = NavRoute.FirstRoute

    @Composable
    override fun getHeaderState(): BottomSheetHeaderState {
        return BottomSheetHeaderState(
            title = "First route",
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
        val navBackStack = LocalBottomSheetNavBackStack.current
        return BottomSheetFooterState(
            textPrimary = "Next",
            onPrimaryButtonClicked = {
                runCatching {
                    navBackStack.add(NavRoute.SecondRoute)
                }.onFailure {
                    Toast.makeText(context, "Failed to navigate", Toast.LENGTH_SHORT).show()
                    Log.e(TAG, it.message.orEmpty(), it)
                }
            }
        )
    }

    @Composable
    override fun getContent(
        viewModel: DashboardViewModel,
        arguments: NavKey,
        sheetState: SheetState,
        scrollState: ScrollState,
        onCancel: () -> Unit,
    ) {
        val navBackStack = LocalBottomSheetNavBackStack.current
        FirstContent()
        BackHandler(onBack = navBackStack::removeLastOrNull)
    }
}

@Composable
private fun FirstContent() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "First content")
    }
}

@Preview
@Composable
private fun Preview() {
    MyABCBottomSheetTheme(
        headerState = BottomSheetHeaderState(title = "First Route"),
        footerState = BottomSheetFooterState(textPrimary = "Next")
    ) {
        FirstContent()
    }
}