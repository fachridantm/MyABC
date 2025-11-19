package com.outivox.myabc.ui.organisms

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.outivox.core.theme.colorOnTertiaryContainer
import com.outivox.myabc.R
import com.outivox.myabc.ui.atoms.Avatar


data class ProfileComponentAttribute(
    @DrawableRes val avatarImageRes: Int,
    val userName: String,
    val userEmail: String,
)

@Composable
fun ProfileComponent(modifier: Modifier = Modifier, attribute: ProfileComponentAttribute) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Avatar(imageRes = attribute.avatarImageRes, size = 72.dp)
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = attribute.userName, fontWeight = FontWeight.Bold, fontSize = 28.sp)
            Text(text = attribute.userEmail, fontSize = 18.sp, color = colorOnTertiaryContainer)
        }
    }
}

@Preview
@Composable
private fun ProfileComponentPreview() {
    ProfileComponent(
        attribute = ProfileComponentAttribute(
            avatarImageRes = R.drawable.img_man_side_view,
            userName = "Alex Doe",
            userEmail = "alex.doe@email.com",
        ),
    )
}
