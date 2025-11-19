package com.outivox.myabc.ui.organisms

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.outivox.core.theme.colorOnSecondaryContainer
import com.outivox.core.theme.colorOnTertiary
import com.outivox.core.theme.colorPrimaryContainer
import com.outivox.myabc.R

@Composable
private fun BottomNavMenu(
    @DrawableRes iconRes: Int,
    title: String,
    isActive: Boolean = false,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(if (isActive) colorPrimaryContainer else Color.White)
                .padding(horizontal = 12.dp, vertical = 2.dp)
                .size(24.dp),
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = if (isActive) colorOnTertiary else colorOnSecondaryContainer,
        )
        Text(
            text = title,
            fontSize = 14.sp,
            color = if (isActive) colorOnTertiary else colorOnSecondaryContainer,
        )
    }
}

@Composable
fun BottomNavBar(onClick: (() -> Unit)? = null) {
    Column {
        HorizontalDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            BottomNavMenu(
                title = "Home",
                iconRes = R.drawable.ic_home_outline,
                isActive = true,
                onClick = { onClick?.invoke() },
            )
            BottomNavMenu(
                title = "Cards",
                iconRes = R.drawable.ic_card,
                isActive = false,
                onClick = { onClick?.invoke() },
            )
            BottomNavMenu(
                title = "History",
                iconRes = R.drawable.ic_history,
                isActive = false,
                onClick = { onClick?.invoke() },
            )
            BottomNavMenu(
                title = "Profile",
                iconRes = R.drawable.ic_profile_outline,
                isActive = false,
                onClick = { onClick?.invoke() },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomNavMenuPreview() {
    BottomNavMenu(
        title = "Profile",
        iconRes = R.drawable.ic_transfer,
        isActive = false,
        onClick = {},
    )
}


@Preview
@Composable
private fun BottomNavBarPreview() {
    BottomNavBar()
}
