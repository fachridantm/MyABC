package com.outivox.core.bottomsheet

import androidx.compose.foundation.ScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.outivox.core.component.BottomSheetFooterState
import com.outivox.core.component.BottomSheetHeaderState
import com.outivox.core.util.LocalBottomSheetNavBackStack

/**
 * Interface for creating bottom sheet routes.
 *
 * @param VM The type of the ViewModel associated with this route.
 *
 ***/
interface BottomSheetRoute<VM> {
    @Composable
    fun createRoute(
        onCancel: () -> Unit,
        viewModel: VM,
    ): BottomSheetRoutes
}

/**
 * Abstract base class for creating bottom sheet routes.
 *
 * @param VM The type of the ViewModel associated with this route.
 *
 **/
@OptIn(ExperimentalMaterial3Api::class)
abstract class BaseBottomSheetRoute<VM> : BottomSheetRoute<VM> {
    /**
     * Returns the navigation route as a string.
     **/
    @Composable
    protected abstract fun getNavRoute(): NavKey

    /**
     * Returns the list of named navigation arguments for this route.
     *
     * @return List of NamedNavArgument.
     **/
    @Composable
    protected open fun getMetadata(): Map<String, Any> = emptyMap()

    /**
     * Returns the header state for this route.
     *
     * @return BottomSheetHeaderState
     **/
    @Composable
    protected open fun getHeaderState(): BottomSheetHeaderState = BottomSheetHeaderState()

    /**
     * Returns the footer state for this route.
     *
     * @return BottomSheetFooterState
     **/
    @Composable
    protected open fun getFooterState(): BottomSheetFooterState = BottomSheetFooterState()

    /**
     * Composable function to define the content of the bottom sheet.
     *
     * @param viewModel The ViewModel associated with this route.
     * @param arguments The navigation back stack entry.
     * @param sheetState The state of the bottom sheet.
     * @param onCancel The callback to be invoked when the cancel action is triggered.
     **/
    @Composable
    protected abstract fun getContent(
        viewModel: VM,
        arguments: NavKey,
        sheetState: SheetState,
        scrollState: ScrollState,
        onCancel: () -> Unit,
    )

    /**
     * Indicates whether to add vertical scroll to the bottom sheet content.
     *
     * @return True if vertical scroll should be added, false otherwise.
     **/
    @Composable
    protected open fun addVerticalScroll(): Boolean = true

    /**
     * Returns the dismiss action for the bottom sheet.
     *
     * @param viewModel The ViewModel associated with this route.
     * @param onCancel The callback to be invoked when the cancel action is triggered.
     * @return Dismiss action as a lambda function.
     **/
    @Composable
    protected open fun onDragDismiss(
        navBackStack: NavBackStack<NavKey>,
        viewModel: VM,
        onCancel: () -> Unit,
    ): () -> Boolean = {
        navBackStack.removeLastOrNull()
        false
    }

    /**
     * Returns the dismiss action for the grey area of the bottom sheet.
     *
     * @param viewModel The ViewModel associated with this route.
     * @param onCancel The callback to be invoked when the cancel action is triggered.
     * @return Dismiss action as a lambda function.
     **/
    @Composable
    protected open fun onGreyAreaDismiss(
        navBackStack: NavBackStack<NavKey>,
        viewModel: VM,
        onCancel: () -> Unit,
    ): () -> Boolean = onDragDismiss(navBackStack, viewModel, onCancel)

    /**
     * Creates the bottom sheet route with the provided parameters.
     *
     * @param onCancel The callback to be invoked when the cancel action is triggered.
     * @param viewModel The ViewModel associated with this route.
     * @return BottomSheetRoutes configuration.
     **/
    @Composable
    override fun createRoute(
        onCancel: () -> Unit,
        viewModel: VM,
    ): BottomSheetRoutes {
        val navBackStack = LocalBottomSheetNavBackStack.current
        return BottomSheetRoutes(
            navRoute = getNavRoute(),
            metadata = getMetadata(),
            headerState = getHeaderState(),
            footerState = getFooterState(),
            content = { arguments, sheetState, scrollState ->
                getContent(viewModel, arguments, sheetState, scrollState, onCancel)
            },
            isAddVerticalScroll = addVerticalScroll(),
            onDragDismiss = onDragDismiss(navBackStack, viewModel, onCancel),
            onGreyAreaDismiss = onGreyAreaDismiss(navBackStack, viewModel, onCancel),
        )
    }
}