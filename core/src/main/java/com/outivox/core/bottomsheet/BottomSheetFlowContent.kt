@file:OptIn(ExperimentalMaterial3Api::class)

package com.outivox.core.bottomsheet

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import com.outivox.core.component.BottomSheetFooterState
import com.outivox.core.component.BottomSheetHeaderState
import com.outivox.core.component.FooterBottomSheet
import com.outivox.core.component.HeaderBottomSheet
import com.outivox.core.theme.colorPrimaryContainer

data class BottomSheetRoutes(
    val navRoute: String,
    val arguments: List<NamedNavArgument>? = null,
    val title: String = "",
    val headerState: BottomSheetHeaderState,
    val footerState: BottomSheetFooterState,
    val content: @Composable ((NavBackStackEntry, SheetState, ScrollState) -> Unit),
    var isAddVerticalScroll: Boolean = true,
    val onDragDismiss: () -> Boolean = { true },
    val onGreyAreaDismiss: () -> Boolean = onDragDismiss,
)

@Composable
fun BottomSheetFlowContent(
    bottomSheetBackground: Color = colorPrimaryContainer,
    bottomSheetFlow: BottomSheetFlow = rememberBottomSheetFlow(),
    routes: List<BottomSheetRoutes>,
    onCancel: () -> Unit,
) {
    val scrollState = rememberScrollState()

    BaseBottomSheetFlow(
        bottomSheetFlow = bottomSheetFlow,
        bottomSheetBackground = bottomSheetBackground,
        onCancel = onCancel,
    ) {
        if (routes.isNotEmpty()) {
            routes.forEach { bottomSheetState ->
                route(
                    route = bottomSheetState.navRoute,
                    arguments = bottomSheetState.arguments,
                    onDragDismiss = bottomSheetState.onDragDismiss,
                    onGreyAreaDismiss = bottomSheetState.onGreyAreaDismiss,
                ) { sheetState, navBackStackEntry ->
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        // Parent of Header and Content
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                        ) {
                            // Header
                            BottomSheetHeader(bottomSheetState.headerState)

                            // Content
                            Column(
                                modifier = Modifier.apply {
                                    if (bottomSheetState.isAddVerticalScroll) {
                                        this.verticalScroll(scrollState)
                                    }
                                },
                            ) {
                                bottomSheetState.content.invoke(navBackStackEntry, sheetState, scrollState)
                            }
                        }

                        // Footer
                        BottomSheetFooter(bottomSheetState.footerState)
                    }
                }
            }
        }
    }
}

@Composable
fun BottomSheetHeader(state: BottomSheetHeaderState) {
    HeaderBottomSheet(state)
}

@Composable
fun BottomSheetFooter(state: BottomSheetFooterState) {
    FooterBottomSheet(state)
}