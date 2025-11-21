package com.outivox.core.bottomsheet

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.outivox.core.component.DragHandleBottomSheet
import com.outivox.core.navigation.slideInFromEnd
import com.outivox.core.navigation.slideOutToStart
import com.outivox.core.theme.colorPrimaryContainer
import com.outivox.core.util.LocalBottomSheetNavBackStack

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
        route: NavKey,
        metadata: Map<String, Any>,
        onDragDismiss: () -> Boolean = { true },
        onGreyAreaDismiss: () -> Boolean = { true },
        isBottomSheet: Boolean = true,
        content: @Composable (SheetState, NavKey) -> Unit,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseBottomSheetFlow(
    modifier: Modifier = Modifier,
    bottomSheetBackground: Color = colorPrimaryContainer,
    onCancel: () -> Unit,
    configuration: BottomSheetFlowScope.() -> Unit,
) {
    val bottomSheetNavBackStack = LocalBottomSheetNavBackStack.current
    NavDisplay(
        backStack = bottomSheetNavBackStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            // Then add the view model store decorator
            rememberViewModelStoreNavEntryDecorator(),
        ),
        onBack = {
            bottomSheetNavBackStack.removeLastOrNull()
        },
        entryProvider = entryProvider {
            val flowScope = object : BottomSheetFlowScope {
                override fun route(
                    route: NavKey,
                    metadata: Map<String, Any>,
                    onDragDismiss: () -> Boolean,
                    onGreyAreaDismiss: () -> Boolean,
                    isBottomSheet: Boolean,
                    content: @Composable ((SheetState, NavKey) -> Unit),
                ) {
                    entry(
                        key = route,
                        metadata = metadata
                    ) { arguments ->
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
                                content.invoke(bottomSheetState, arguments)
                            }
                        }
                    }
                }
            }

            configuration.invoke(flowScope)
            // Add other navGraph destinations here
        },
        transitionSpec = {
            // Slide in from right when navigating forward
            slideInFromEnd() togetherWith ExitTransition.KeepUntilTransitionsFinished
        },
        popTransitionSpec = {
            // Slide in from left when navigating back
            EnterTransition.None togetherWith slideOutToStart()
        },
        predictivePopTransitionSpec = {
            // Slide in from left when navigating back
            EnterTransition.None togetherWith slideOutToStart()
        },
    )
}
