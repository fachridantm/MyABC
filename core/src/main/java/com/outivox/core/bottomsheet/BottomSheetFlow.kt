package com.outivox.core.bottomsheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.outivox.core.component.DragHandleBottomSheet
import com.outivox.core.navigation.slideInFromEnd
import com.outivox.core.navigation.slideInFromStart
import com.outivox.core.navigation.slideOutToEnd
import com.outivox.core.navigation.slideOutToStart
import com.outivox.core.theme.colorPrimaryContainer
import com.outivox.core.util.LocalBottomSheetNavController

/**
 * Configuration scope for the BottomSheetFlow.
 * @see BottomSheetFlow
 */
interface BottomSheetFlowScope {
    /**
     * Adding route to the flow.
     * @param route is the route id
     * @param onDragDismiss Return true = dismiss bottomsheet. Return false = prevent dismissal.
     * When the user drag the bottomsheet to the bottom with dismissal intention, the `onDragDismiss`
     * event will be invoked.
     * @param onGreyAreaDismiss Return true = dismiss bottomsheet. Return false = prevent dismissal.
     * When the user click the grey area to dismiss the bottomsheet, the `onGreyAreaDismiss` event
     * will be invoked.
     * @param content is the composable content.
     */
    @OptIn(ExperimentalMaterial3Api::class)
    fun route(
        route: String,
        arguments: List<NamedNavArgument>?,
        onDragDismiss: () -> Boolean = { true },
        onGreyAreaDismiss: () -> Boolean = { true },
        isBottomSheet: Boolean = true,
        content: @Composable (SheetState, NavBackStackEntry) -> Unit,
    )
}

data class BottomSheetFlow(
    val startDestination: String,
)

@Composable
fun rememberBottomSheetFlow(
    startDestination: String = "",
    key: Any = Unit,
): BottomSheetFlow = remember(key) {
    BottomSheetFlow(
        startDestination = startDestination,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseBottomSheetFlow(
    modifier: Modifier = Modifier,
    bottomSheetBackground: Color = colorPrimaryContainer,
    bottomSheetFlow: BottomSheetFlow = rememberBottomSheetFlow(),
    onCancel: () -> Unit,
    configuration: BottomSheetFlowScope.() -> Unit,
) {
    val bottomSheetNavController = LocalBottomSheetNavController.current
    NavHost(
        navController = bottomSheetNavController,
        startDestination = bottomSheetFlow.startDestination,
    ) {
        val flowScope = object : BottomSheetFlowScope {
            override fun route(
                route: String,
                arguments: List<NamedNavArgument>?,
                onDragDismiss: () -> Boolean,
                onGreyAreaDismiss: () -> Boolean,
                isBottomSheet: Boolean,
                content: @Composable ((SheetState, NavBackStackEntry) -> Unit),
            ) {
                composable(
                    route = route,
                    arguments = arguments.orEmpty(),
                    enterTransition = { slideInFromStart() },
                    exitTransition = { slideOutToEnd() },
                    popEnterTransition = { slideInFromEnd() },
                    popExitTransition = { slideOutToStart() },
                ) { navBackEntry ->
                    /** Get the current state of the bottom sheet **/
                    val bottomSheetState = rememberModalBottomSheetState(
                        confirmValueChange = { true },
                        skipPartiallyExpanded = true,
                    )

                    ModalBottomSheet(
                        modifier = modifier.systemBarsPadding(),
                        sheetState = bottomSheetState,
                        containerColor = bottomSheetBackground,
                        onDismissRequest = onCancel,
                        dragHandle = {
                            DragHandleBottomSheet()
                        },
                        shape = RoundedCornerShape(
                            topStart = 24.dp,
                            topEnd = 24.dp,
                        ),
                    ) {
                        Column(
                            modifier = modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            content.invoke(bottomSheetState, navBackEntry)
                        }
                    }
                }
            }
        }

        configuration.invoke(flowScope)
    }
}
