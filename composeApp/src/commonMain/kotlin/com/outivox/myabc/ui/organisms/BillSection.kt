package com.outivox.myabc.ui.organisms

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.outivox.myabc.core.theme.colorPrimary
import com.outivox.myabc.core.theme.colorSecondaryContainer
import com.outivox.myabc.generated.resources.Res
import com.outivox.myabc.generated.resources.ic_light_bulb
import com.outivox.myabc.generated.resources.ic_netflix_square
import com.outivox.myabc.ui.atoms.SurfaceIcon
import com.outivox.myabc.ui.molecules.ResourceItem
import com.outivox.myabc.ui.molecules.ResourceItemAttribute
import org.jetbrains.compose.resources.DrawableResource


data class BillSectionResourceItemAttribute(
    val iconRes: DrawableResource,
    val iconBackgroundColor: Color,
    val label: String,
    val description: String,
    val price: String,
)

@Composable
fun BillSection(
    modifier: Modifier = Modifier,
    data: List<BillSectionResourceItemAttribute>,
    onClickViewAll: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "Upcoming Bills", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(
                modifier = Modifier.clickable(onClick = onClickViewAll),
                text = "View All",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorPrimary,
            )
        }
        data.forEach { item ->
            Card(
                colors = CardDefaults.cardColors(colorSecondaryContainer),
            ) {
                ResourceItem(
                    modifier = Modifier.padding(16.dp),
                    attribute = ResourceItemAttribute(
                        label = item.label,
                        description = item.description,
                    ),
                    leadingContent = {
                        SurfaceIcon(
                            iconRes = item.iconRes,
                            size = 28.dp,
                            iconPadding = 8.dp,
                            iconColor = Color.Unspecified,
                            backgroundColor = item.iconBackgroundColor,
                        )
                    },
                    trailingContent = {
                        Text(
                            text = item.price,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    },
                )
            }
        }
    }
}

@Preview
@Composable
private fun DashboardBillSectionPreview() {
    BillSection(
        onClickViewAll = {},
        data = listOf(
            BillSectionResourceItemAttribute(
                iconRes = Res.drawable.ic_netflix_square,
                label = "Netflix",
                description = "Due: Oct 28",
                price = "$15.49",
                iconBackgroundColor = Color.Magenta,
            ),
            BillSectionResourceItemAttribute(
                iconRes = Res.drawable.ic_light_bulb,
                label = "City Power",
                description = "Due: Nov 02",
                price = "$78.20",
                iconBackgroundColor = Color.Yellow,
            ),
        ),
    )
}