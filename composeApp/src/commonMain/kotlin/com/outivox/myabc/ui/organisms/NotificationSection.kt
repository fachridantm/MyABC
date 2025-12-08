package com.outivox.myabc.ui.organisms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
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
import com.outivox.myabc.core.theme.colorOnPrimaryContainer
import com.outivox.myabc.core.theme.colorPrimary
import com.outivox.myabc.core.theme.colorPrimaryContainer
import com.outivox.myabc.core.theme.colorTertiaryContainer
import com.outivox.myabc.generated.resources.Res
import com.outivox.myabc.generated.resources.ic_card_outline
import com.outivox.myabc.ui.atoms.SurfaceIcon
import com.outivox.myabc.ui.molecules.ResourceItem
import com.outivox.myabc.ui.molecules.ResourceItemAttribute
import org.jetbrains.compose.resources.DrawableResource


data class NotificationResourceItemAttribute(
    val iconRes: DrawableResource,
    val label: String,
    val description: String,
    val timestamp: String,
    val isUnread: Boolean = true,
)

data class NotificationSectionAttribute(
    val date: String,
    val itemList: List<NotificationResourceItemAttribute> = emptyList(),
)

@Composable
fun NotificationSection(attribute: NotificationSectionAttribute) {
    Column(modifier = Modifier.background(Color.White)) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = attribute.date,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = colorPrimary,
        )

        Column(modifier = Modifier.padding(bottom = 16.dp)) {
            attribute.itemList.forEach { item ->
                ResourceItem(
                    modifier = Modifier
                        .background(
                            if (item.isUnread) colorTertiaryContainer
                            else Color.White,
                        )
                        .padding(16.dp),
                    verticalAlignment = Alignment.Top,
                    attribute = ResourceItemAttribute(
                        label = item.label,
                        description = item.description,
                    ),
                    leadingContent = {
                        SurfaceIcon(
                            iconRes = item.iconRes,
                            size = 20.dp,
                            iconPadding = 12.dp,
                            iconColor = colorOnPrimaryContainer,
                            backgroundColor = colorPrimaryContainer,
                        )
                    },
                    trailingContent = {
                        Column(
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(text = item.timestamp, fontSize = 12.sp, color = Color.DarkGray)
                            if (item.isUnread) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(Color.Red),
                                )
                            }
                        }
                    },
                )
                HorizontalDivider()
            }
        }
    }
}

@Preview
@Composable
private fun NotificationSectionPreview() {
    NotificationSection(
        attribute = NotificationSectionAttribute(
            date = "Today",
            itemList = listOf(
                NotificationResourceItemAttribute(
                    iconRes = Res.drawable.ic_card_outline,
                    label = "Card Purchase",
                    description = "$50.00 at Starbucks",
                    timestamp = "10:45 AM",
                ),
                NotificationResourceItemAttribute(
                    iconRes = Res.drawable.ic_card_outline,
                    label = "Card Purchase",
                    description = "$50.00 at Starbucks",
                    timestamp = "10:45 AM",
                    isUnread = false,
                ),
            ),
        ),
    )
}
