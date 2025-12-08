package com.outivox.myabc.core.navigation

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically

fun slideInFromEnd() = slideInHorizontally(
    initialOffsetX = { it },
    animationSpec = tween(200, easing = FastOutSlowInEasing),
)

fun slideOutToStart() = slideOutHorizontally(
    targetOffsetX = { -it },
    animationSpec = tween(200, easing = FastOutSlowInEasing),
)

fun slideInFromStart() = slideInHorizontally(
    initialOffsetX = { -it },
    animationSpec = tween(200, easing = FastOutSlowInEasing),
)

fun slideOutToEnd() = slideOutHorizontally(
    targetOffsetX = { it },
    animationSpec = tween(200, easing = FastOutSlowInEasing),
)

fun slideUpFromBottom() = slideInVertically(
    initialOffsetY = { it },
    animationSpec = tween(200, easing = FastOutLinearInEasing),
)

fun slideDownToBottom() = slideOutVertically(
    targetOffsetY = { -it },
    animationSpec = tween(200, easing = FastOutLinearInEasing),
)

fun slideUpFromTop() = slideInVertically(
    initialOffsetY = { -it },
    animationSpec = tween(200, easing = FastOutLinearInEasing),
)

fun slideDownToTop() = slideOutVertically(
    targetOffsetY = { it },
    animationSpec = tween(200, easing = FastOutLinearInEasing),
)