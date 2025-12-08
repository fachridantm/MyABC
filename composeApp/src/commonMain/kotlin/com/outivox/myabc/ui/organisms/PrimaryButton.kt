package com.outivox.myabc.ui.organisms

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.outivox.myabc.core.theme.colorOnPrimary
import com.outivox.myabc.core.theme.colorOnTertiaryContainer
import com.outivox.myabc.generated.resources.Res
import com.outivox.myabc.generated.resources.ic_logout_outline
import com.outivox.myabc.generated.resources.ic_transfer
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    leadingIconRes: DrawableResource? = null,
    trailingIconRes: DrawableResource? = null,
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
        leadingIconRes = Res.drawable.ic_logout_outline,
        trailingIconRes = Res.drawable.ic_transfer,
        onClick = {}
    )
}