package com.outivox.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.outivox.core.theme.MyABCTheme
import com.outivox.core.theme.colorOnPrimary
import com.outivox.core.theme.colorPrimary

data class BottomSheetFooterState(
    val background: Color = Color.White,
    val textPrimary: String = "",
    val textColorPrimary: Color = colorOnPrimary,
    val colorPrimaryButton: Color = colorPrimary,
    val onPrimaryButtonClicked: () -> Unit = {},
)

@Composable
fun FooterBottomSheet(
    state: BottomSheetFooterState,
    modifier: Modifier = Modifier,
) {
    state.apply {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(
                    color = background,
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                    ),
                ),
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorPrimaryButton,
                ),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                onClick = onPrimaryButtonClicked,
            ) {
                Text(text = textPrimary, color = textColorPrimary)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    MyABCTheme {
        FooterBottomSheet(
            state = BottomSheetFooterState(
                textPrimary = "Next",
            )
        )
    }
}