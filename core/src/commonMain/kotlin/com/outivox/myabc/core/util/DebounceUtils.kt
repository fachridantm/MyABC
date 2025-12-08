package com.outivox.myabc.core.util

import android.os.SystemClock
import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

/**
 * A composable wrapper that provides a debounced click action to its [content].
 *
 * Prevents multiple rapid invocations of [onClick] within a defined debounce interval (default: 1500ms).
 * Useful for wrapping composables like Button, Box, Card, etc. where you want to safely pass a click handler.
 *
 * @param onClick The action to invoke safely (only once per debounce interval).
 * @param content The composable that receives the safe click lambda.
 *
 * @sample
 * SafeClickWrapper(onClick = { navigate() }) { onSafeClick ->
 *     Button(onClick = onSafeClick) {
 *         Text("Click Me")
 *     }
 * }
 */
@Composable
fun SafeClickWrapper(onClick: () -> Unit, content: @Composable (() -> Unit) -> Unit) {
    val clickAction = rememberSafeClickableState { onClick.invoke() }
    content(clickAction)
}

/**
 * A modifier that extends [Modifier.clickable] with internal debounce logic to prevent rapid repeated clicks.
 *
 * Can be safely used to wrap click actions that should only be triggered once per debounce interval.
 *
 * @param enabled Whether the clickable is enabled.
 * @param onClickLabel Optional accessibility label.
 * @param role Optional accessibility role.
 * @param onClick The action to invoke when clicked, safely debounced.
 */
@Composable
fun Modifier.safeClickable(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit,
): Modifier {
    val clickAction = rememberSafeClickableState { onClick.invoke() }

    return this.clickable(
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role,
        onClick = clickAction,
    )
}

/**
 * A variant of [safeClickable] that allows custom [interactionSource] and [indication] parameters.
 *
 * This gives you control over ripple effect and interaction tracking, while still protecting against rapid clicks.
 *
 * @param interactionSource The interaction source to track pressed, focused, etc.
 * @param indication The ripple or visual feedback to show.
 * @param enabled Whether the clickable is enabled.
 * @param onClickLabel Optional accessibility label.
 * @param role Optional accessibility role.
 * @param onClick The action to invoke when clicked, safely debounced.
 */
@Composable
fun Modifier.safeClickable(
    interactionSource: MutableInteractionSource?,
    indication: Indication?,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit,
): Modifier {
    val clickAction = rememberSafeClickableState { onClick.invoke() }

    return this.clickable(
        interactionSource = interactionSource,
        indication = indication,
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role,
        onClick = clickAction,
    )
}

/**
 * Remembers and returns a debounced click lambda that ensures [onClick] is only triggered once
 * per debounce interval (default: 1500ms).
 *
 * Internally uses [SystemClock.elapsedRealtime] to compare click timestamps and state-controlled
 * enabling logic to suppress rapid re-entrance.
 *
 * This function should be used strictly within a Compose context (inside a Composable), and is
 * the foundation for [SafeClickWrapper] and [Modifier.safeClickable].
 *
 * @param onClick The action to invoke safely.
 * @return A lambda that triggers [onClick] with debounce protection.
 *
 * @see SafeClickWrapper
 * @see Modifier.safeClickable
 */
@Composable
private fun rememberSafeClickableState(onClick: () -> Unit): () -> Unit {
    val coroutineScope = rememberCoroutineScope()
    val debounceInterval = 1500L
    val lastTimeClick = remember { mutableLongStateOf(0L) }
    val isEnabled = remember { AtomicBoolean(true) }
    val clickAction = {
        val now = SystemClock.elapsedRealtime()
        if (isEnabled.get() && now - lastTimeClick.longValue > debounceInterval) {
            isEnabled.set(false)
            lastTimeClick.longValue = now
            onClick()
            coroutineScope.launch {
                delay(debounceInterval)
                isEnabled.set(true)
            }
        }
    }
    return clickAction
}