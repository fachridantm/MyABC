package com.outivox.myabc.ui.organisms

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.outivox.core.theme.colorOnPrimary
import com.outivox.core.theme.colorOnSecondaryContainer
import com.outivox.core.theme.colorPrimary
import com.outivox.myabc.R
import com.outivox.myabc.ui.atoms.SurfaceIcon
import com.outivox.myabc.ui.molecules.ResourceItem
import com.outivox.myabc.ui.molecules.ResourceItemAttribute

data class MenuResourceItemAttribute(
    @DrawableRes val iconRes: Int,
    val label: String,
    val description: String? = null,
)

data class SettingMenuSectionAttribute(
    val title: String,
    val itemList: List<MenuResourceItemAttribute> = emptyList(),
)

// TODO (Step 2.3): Support onItemClick callback for individual menu items.
// In UDF architecture, child organisms emit click events upward to the parent screen.
@Composable
fun SettingMenuSection(
    attribute: SettingMenuSectionAttribute,
    onItemClick: ((String) -> Unit)? = null,
) {
    Column(
        modifier = Modifier.background(Color.White),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = attribute.title,
            fontWeight = FontWeight.Medium,
            color = colorPrimary,
        )

        attribute.itemList.forEachIndexed { index, item ->
            ResourceItem(
                modifier = Modifier.clickable {
                    onItemClick?.invoke(item.label)
                },
                attribute = ResourceItemAttribute(
                    label = item.label,
                    description = item.description,
                ),
                leadingContent = {
                    SurfaceIcon(
                        iconRes = item.iconRes,
                        size = 28.dp,
                        iconPadding = 12.dp,
                        iconColor = colorOnSecondaryContainer,
                        backgroundColor = colorOnPrimary,
                    )
                },
                trailingContent = {
                    Icon(
                        painter = painterResource(R.drawable.ic_chevron_next),
                        contentDescription = null,
                        tint = Color.Gray,
                    )
                },
            )
        }

        HorizontalDivider(color = Color.LightGray)
    }
}

@Preview
@Composable
private fun SettingMenuSectionPreview() {
    SettingMenuSection(
        attribute = SettingMenuSectionAttribute(
            title = "PERSONAL INFORMATION",
            itemList = listOf(
                MenuResourceItemAttribute(
                    iconRes = R.drawable.ic_setting,
                    label = "Contact Details",
                    description = "Manage your phone and email",
                ),
                MenuResourceItemAttribute(
                    iconRes = R.drawable.ic_setting,
                    label = "Mailing Address",
                ),
            ),
        ),
    )
}