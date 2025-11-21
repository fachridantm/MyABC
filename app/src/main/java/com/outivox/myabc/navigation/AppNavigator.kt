package com.outivox.myabc.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.outivox.authentication.ui.presentation.login.navigation.authNavGraph
import com.outivox.core.navigation.slideInFromEnd
import com.outivox.core.navigation.slideOutToStart
import com.outivox.core.util.LocalNavBackStack
import com.outivox.myabc.ui.navigation.mainNavGraph

@Composable
fun AppNavigator() {
    val navBackStack = LocalNavBackStack.current
    NavDisplay(
        backStack = navBackStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            // Then add the view model store decorator
            rememberViewModelStoreNavEntryDecorator(),
        ),
        onBack = {
            navBackStack.removeLastOrNull()
        },
        entryProvider = entryProvider {
            mainNavGraph()
            authNavGraph()
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