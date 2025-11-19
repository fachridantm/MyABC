package com.outivox.myabc.ui.organisms

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.outivox.core.theme.colorOnPrimary
import com.outivox.core.theme.colorOnTertiaryContainer
import com.outivox.myabc.R

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    @DrawableRes leadingIconRes: Int? = null,
    @DrawableRes trailingIconRes: Int? = null,
    text: String,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        shape = CircleShape,
        border = BorderStroke(width = 1.dp, color = colorOnTertiaryContainer),
        colors = ButtonDefaults.buttonColors(containerColor = colorOnPrimary),
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            leadingIconRes?.let {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(it),
                    contentDescription = null,
                    tint = Color.Red,
                )
            }
            Text(text = text, color = Color.Red, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            trailingIconRes?.let {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(it),
                    contentDescription = null,
                    tint = Color.Red,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PrimaryButtonPreview() {
    PrimaryButton(
        modifier = Modifier.padding(16.dp),
        text = "Text",
        leadingIconRes = R.drawable.ic_logout_outline,
        trailingIconRes = R.drawable.ic_transfer,
        onClick = {}
    )
}