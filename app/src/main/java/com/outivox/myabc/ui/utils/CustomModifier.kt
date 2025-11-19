package com.outivox.myabc.ui.utils

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.outivox.core.theme.colorOnPrimaryContainer
import com.outivox.core.theme.colorPrimaryContainer
import com.outivox.myabc.R
import com.outivox.myabc.ui.atoms.SurfaceIcon
import com.outivox.myabc.ui.molecules.ResourceItem
import com.outivox.myabc.ui.molecules.ResourceItemAttribute

private const val Z_LEFT_ROTATION = -4f
private const val Z_RIGHT_ROTATION = 4f
private const val ROTATION_SPEED = 300

fun Modifier.wiggle(
    enabled: Boolean = true,
    rotationSpeed: Int = ROTATION_SPEED,
    maxLeftRotation: Float = Z_LEFT_ROTATION,
    maxRightRotation: Float = Z_RIGHT_ROTATION,
) = composed {
    val transition = rememberInfiniteTransition(label = "")
    val rotation by transition.animateFloat(
        initialValue = maxLeftRotation,
        targetValue = maxRightRotation,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = rotationSpeed, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "Wiggle",
    )

    if (enabled) {
        Modifier.graphicsLayer(rotationZ = rotation)
    } else {
        Modifier
    }
}

@Preview
@Composable
private fun WigglePreview() {
    SurfaceIcon(
        modifier = Modifier.wiggle(),
        iconRes = R.drawable.ic_transfer
    )
}

private const val SHIMMER_DURATION = 1000

@Composable
fun Modifier.shimmer(show: Boolean = true, cornerRadius: Dp = 8.dp): Modifier {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition(label = "Shimmer")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(SHIMMER_DURATION),
        ),
        label = "Shimmer",
    )

    return this
        .drawWithContent {
            if (show) {
                drawRoundRect(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.Gray,
                            Color.Black,
                            Color.Gray,
                        ),
                        start = Offset(startOffsetX, 0f),
                        end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat()),
                    ),
                    alpha = .3f,
                    size = size.toSize(),
                    cornerRadius = CornerRadius(cornerRadius.toPx()),
                )
            } else {
                drawContent()
            }
        }
        .onSizeChanged { size = it }
}

@Preview(showBackground = true)
@Composable
private fun ShimmerPreview() {
    ResourceItem(
        modifier = Modifier.padding(24.dp),
        verticalAlignment = Alignment.Top,
        attribute = ResourceItemAttribute(
            modifier = Modifier.shimmer(),
            label = "Card Purchase",
            description = "$50.00 at Starbucks",
        ),
        leadingContent = {
            SurfaceIcon(
                iconRes = R.drawable.ic_card,
                size = 28.dp,
                iconPadding = 8.dp,
                iconColor = colorOnPrimaryContainer,
                backgroundColor = colorPrimaryContainer,
            )
        },
        trailingContent = {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    modifier = Modifier.shimmer(),
                    text = "10:45 AM",
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
                Box(
                    modifier = Modifier
                        .shimmer()
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Color.Red)
                )
            }
        },
    )
}
