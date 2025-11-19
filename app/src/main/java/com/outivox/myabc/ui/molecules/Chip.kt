package com.outivox.myabc.ui.molecules

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.outivox.core.theme.colorOnSecondaryContainer
import com.outivox.core.theme.colorSecondaryContainer
import com.outivox.myabc.R

data class ChipAttribute(
    @DrawableRes val leadingIconRes: Int? = null,
    @DrawableRes val trailingIconRes: Int? = null,
    val label: String,
    val isSelected: Boolean = false,
)

@Composable
fun Chip(
    attribute: ChipAttribute,
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .heightIn(min = 40.dp)
            .border(
                width = if (attribute.isSelected) 0.dp else 1.dp,
                color = if (attribute.isSelected) Color.White else colorOnSecondaryContainer,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .background(if (attribute.isSelected) colorSecondaryContainer else Color.White)
            .clickable(
                indication = ripple(),
                interactionSource = null,
                onClick = { onClick?.invoke() },
            )
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        attribute.leadingIconRes?.let {
            Icon(
                modifier = Modifier.size(22.dp),
                painter = painterResource(it),
                contentDescription = null,
                tint = colorOnSecondaryContainer
            )
        }
        Text(
            text = attribute.label,
            fontWeight = FontWeight.Medium,
            color = colorOnSecondaryContainer
        )
        attribute.trailingIconRes?.let {
            Icon(
                modifier = Modifier.size(22.dp),
                painter = painterResource(it),
                contentDescription = null,
                tint = colorOnSecondaryContainer
            )
        }
    }
}

@Preview
@Composable
private fun ChipPreview() {
    Column {
        Chip(
            attribute = ChipAttribute(
                leadingIconRes = R.drawable.ic_date_picker,
                label = "Last 30 days",
                trailingIconRes = R.drawable.ic_chevron_down,
            ),
        )
        Chip(
            attribute = ChipAttribute(label = "All", isSelected = true),
        )
    }
}
