package com.outivox.myabc.ui.organisms

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.outivox.myabc.R
import com.outivox.myabc.core.theme.colorOnPrimaryContainer


@Composable
fun HeaderPage(
    @DrawableRes leadingIconRes: Int? = null,
    @DrawableRes trailingIconRes: Int? = null,
    title: String,
    onClickLeadingIcon: (() -> Unit)? = null,
    onClickTrailingIcon: (() -> Unit)? = null,
    backgroundColor: Color = colorOnPrimaryContainer,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        leadingIconRes?.let {
            Icon(
                modifier = Modifier.clickable(
                    indication = ripple(),
                    interactionSource = null,
                    onClick = { onClickLeadingIcon?.invoke() },
                ),
                painter = painterResource(it),
                contentDescription = null,
                tint = Color.White,
            )
        }
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
        )
        trailingIconRes?.let {
            Icon(
                modifier = Modifier.clickable(
                    indication = ripple(),
                    interactionSource = null,
                    onClick = { onClickTrailingIcon?.invoke() },
                ),
                painter = painterResource(it),
                contentDescription = null,
                tint = Color.White,
            )
        }
    }
}

@Preview
@Composable
private fun HeaderPagePreview() {
    HeaderPage(
        title = "Transactions",
        leadingIconRes = R.drawable.ic_arrow_left,
        trailingIconRes = R.drawable.ic_search,
    )
}
