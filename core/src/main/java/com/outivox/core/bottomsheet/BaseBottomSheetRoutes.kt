package com.outivox.core.bottomsheet

import androidx.compose.foundation.ScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.outivox.core.component.BottomSheetFooterState
import com.outivox.core.component.BottomSheetHeaderState
import com.outivox.core.util.LocalBottomSheetNavController

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
        bottomSheetFlow: BottomSheetFlow,
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
    protected abstract fun getNavRoute(): String

    /**
     * Returns the list of named navigation arguments for this route.
     *
     * @return List of NamedNavArgument.
     **/
    @Composable
    protected open fun getArguments(): List<NamedNavArgument> = emptyList()

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
     * @param bottomSheetFlow The controller for the bottom sheet navigation.
     * @param viewModel The ViewModel associated with this route.
     * @param navBackStackEntry The navigation back stack entry.
     * @param sheetState The state of the bottom sheet.
     * @param onCancel The callback to be invoked when the cancel action is triggered.
     **/
    @Composable
    protected abstract fun getContent(
        bottomSheetFlow: BottomSheetFlow,
        viewModel: VM,
        navBackStackEntry: NavBackStackEntry,
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
     * @param bottomSheetFlow The controller for the bottom sheet navigation.
     * @param viewModel The ViewModel associated with this route.
     * @param onCancel The callback to be invoked when the cancel action is triggered.
     * @return Dismiss action as a lambda function.
     **/
    @Composable
    protected open fun onDragDismiss(
        navController: NavHostController,
        bottomSheetFlow: BottomSheetFlow,
        viewModel: VM,
        onCancel: () -> Unit,
    ): () -> Boolean = {
        navController.popBackStack()
        false
    }

    /**
     * Returns the dismiss action for the grey area of the bottom sheet.
     *
     * @param bottomSheetFlow The controller for the bottom sheet navigation.
     * @param viewModel The ViewModel associated with this route.
     * @param onCancel The callback to be invoked when the cancel action is triggered.
     * @return Dismiss action as a lambda function.
     **/
    @Composable
    protected open fun onGreyAreaDismiss(
        navController: NavHostController,
        bottomSheetFlow: BottomSheetFlow,
        viewModel: VM,
        onCancel: () -> Unit,
    ): () -> Boolean = onDragDismiss(navController, bottomSheetFlow, viewModel, onCancel)

    /**
     * Creates the bottom sheet route with the provided parameters.
     *
     * @param onCancel The callback to be invoked when the cancel action is triggered.
     * @param viewModel The ViewModel associated with this route.
     * @param bottomSheetFlow The controller for the bottom sheet navigation.
     * @return BottomSheetRoutes configuration.
     **/
    @Composable
    override fun createRoute(
        onCancel: () -> Unit,
        viewModel: VM,
        bottomSheetFlow: BottomSheetFlow,
    ): BottomSheetRoutes {
        val navController = LocalBottomSheetNavController.current
        return BottomSheetRoutes(
            navRoute = getNavRoute(),
            arguments = getArguments(),
            headerState = getHeaderState(),
            footerState = getFooterState(),
            content = { navBackStackEntry, sheetState, scrollState ->
                getContent(bottomSheetFlow, viewModel, navBackStackEntry, sheetState, scrollState, onCancel)
            },
            isAddVerticalScroll = addVerticalScroll(),
            onDragDismiss = onDragDismiss(navController, bottomSheetFlow, viewModel, onCancel),
            onGreyAreaDismiss = onGreyAreaDismiss(navController, bottomSheetFlow, viewModel, onCancel),
        )
    }
}