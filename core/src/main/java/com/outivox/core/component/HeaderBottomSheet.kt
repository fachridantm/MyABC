package com.outivox.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.outivox.core.theme.MyABCTheme

data class BottomSheetHeaderState(
    val title: String = "",
    val subtitle: String = "",
    val background: Color = Color.White,
    val leadingIcon: @Composable (() -> Unit)? = null,
    val trailingIcon: @Composable (() -> Unit)? = null,
)

/**
 * HeaderBottomSheet is a composable that is used to display the header of the bottom sheet.
 *
 * @param modifier Modifier that will be applied to the Row.
 * @param title The title of the header.
 * @param subtitle The subtitle of the header.
 * @param leadingAction The leading action of the header. It is a pair of icon drawable and action.
 * @param trailingAction The trailing action of the header. It is a pair of icon drawable and action.
 * @param additionalTrailingAction The additional trailing action of the header. It is a pair of icon drawable and action.
 *
 **/
@Composable
fun HeaderBottomSheet(
    state: BottomSheetHeaderState,
    modifier: Modifier = Modifier,
) {
    state.apply {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(background),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            leadingIcon?.invoke()

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                if (title.isNotEmpty()) {
                    Text(
                        text = title,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                if (subtitle.isNotEmpty()) {
                    Text(
                        text = subtitle,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            trailingIcon?.invoke()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    MyABCTheme {
        HeaderBottomSheet(
            state = BottomSheetHeaderState(
                title = "Title",
                subtitle = "Subtitle",
                leadingIcon = {
                    Icon(
                        modifier = Modifier.requiredSize(24.dp),
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                    )
                },
                trailingIcon = {
                    Icon(
                        modifier = Modifier.requiredSize(24.dp),
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                    )
                }
            )
        )
    }
}