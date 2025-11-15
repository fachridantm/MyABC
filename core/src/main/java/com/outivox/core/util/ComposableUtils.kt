package com.outivox.core.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalView
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.findViewTreeViewModelStoreOwner

/**
 * Retrieves a [ViewModel] from the nearest [androidx.lifecycle.ViewModelStoreOwner] in the Compose hierarchy,
 * or creates a new one if no owner is found.
 *
 * This simplifies ViewModel retrieval in Compose, ensuring a ViewModel is always available,
 * whether within a screen/activity or in isolated composables.
 *
 * @return An instance of the requested [ViewModel] type.
 * @throws IllegalStateException If there is a problem finding the `ViewModelStoreOwner`
 *
 * @sample
 * ```
 * @Composable
 * fun MyComposable() {
 *     val viewModel: MyViewModel = getOrNewViewModelStoreOwner()
 *     // Use viewModel...
 * }
 * ```
 */
@Composable
inline fun <reified T : ViewModel> getOrNewViewModelStoreOwner() = run {
    val composeView = LocalView.current
    val composeViewModelStoreOwner = composeView.findViewTreeViewModelStoreOwner()
    composeViewModelStoreOwner?.let { hiltViewModel<T>(it) } ?: run { hiltViewModel<T>() }
}