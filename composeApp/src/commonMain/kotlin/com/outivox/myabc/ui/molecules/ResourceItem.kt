package com.outivox.myabc.ui.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.outivox.myabc.R
import com.outivox.myabc.core.theme.colorOnPrimaryContainer
import com.outivox.myabc.core.theme.colorPrimaryContainer
import com.outivox.myabc.ui.atoms.SurfaceIcon

data class ResourceItemAttribute(
    val modifier: Modifier = Modifier,
    val label: String? = null,
    val description: String? = null,
    val notes: String? = null,
)

@Composable
fun ResourceItem(
    modifier: Modifier = Modifier,
    attribute: ResourceItemAttribute,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = verticalAlignment,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        leadingContent?.invoke()
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            attribute.label?.let {
                Text(
                    modifier = attribute.modifier.fillMaxWidth(),
                    text = it,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            }
            attribute.description?.let { Text(
                modifier = attribute.modifier.fillMaxWidth(),
                text = it,
                color = Color.DarkGray,
                fontSize = 14.sp
            ) }
            attribute.notes?.let { Text(
                modifier = attribute.modifier.fillMaxWidth(),
                text = it,
                color = Color.Gray,
                fontSize = 12.sp
            ) }
        }
        trailingContent?.invoke()
    }
}

@Preview(showBackground = true)
@Composable
fun ResourceItemPreview() {
    ResourceItem(
        verticalAlignment = Alignment.Top,
        attribute = ResourceItemAttribute(
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
                Text(text = "10:45 AM", fontSize = 12.sp, color = Color.DarkGray)
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Color.Red)
                )
            }
        },
    )
}
