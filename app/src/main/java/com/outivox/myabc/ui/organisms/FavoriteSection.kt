package com.outivox.myabc.ui.organisms

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.outivox.core.theme.colorOnPrimaryContainer
import com.outivox.core.theme.colorPrimary
import com.outivox.core.theme.colorPrimaryContainer
import com.outivox.core.theme.colorSecondaryContainer
import com.outivox.myabc.R
import com.outivox.myabc.ui.atoms.Avatar
import com.outivox.myabc.ui.molecules.ResourceItem
import com.outivox.myabc.ui.molecules.ResourceItemAttribute


data class FavoriteSectionResourceItemAttribute(
    @DrawableRes val imageRes: Int,
    val label: String,
    val description: String,
)

@Composable
fun FavoriteSection(
    modifier: Modifier = Modifier,
    data: List<FavoriteSectionResourceItemAttribute>,
    onClickManage: () -> Unit,
    onClickRepeat: () -> Unit,
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
            Text(text = "Favorites", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(
                modifier = Modifier.clickable(onClick = onClickManage),
                text = "Manage",
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
                        Avatar(
                            imageRes = item.imageRes,
                            size = 40.dp,
                        )
                    },
                    trailingContent = {
                        Text(
                            modifier = Modifier
                                .clickable(onClick = onClickRepeat)
                                .clip(RoundedCornerShape(24.dp))
                                .background(colorPrimaryContainer)
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            text = "Repeat",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = colorOnPrimaryContainer,
                        )
                    },
                )
            }
        }
    }
}


@Preview
@Composable
private fun FavoriteSectionPreview() {
    FavoriteSection(
        onClickManage = {},
        onClickRepeat = {},
        data = listOf(
            FavoriteSectionResourceItemAttribute(
                imageRes = R.drawable.img_mom_portrait,
                label = "Mom",
                description = "Last transfer: $50.00",
            ),
            FavoriteSectionResourceItemAttribute(
                imageRes = R.drawable.img_landlord_portrait,
                label = "Landlord",
                description = "Last transfer: $1200.00",
            ),
        ),
    )
}
