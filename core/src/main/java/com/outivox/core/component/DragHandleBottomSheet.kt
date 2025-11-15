package com.outivox.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun DragHandleBottomSheet(modifier: Modifier = Modifier) {
    Spacer(
        modifier = modifier
            .padding(vertical = 12.dp)
            .width(60.dp)
            .height((3.5).dp)
            .background(Color.Gray),
    )
}