package com.outivox.myabc.ui.presentation.setting

import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.outivox.myabc.R
import com.outivox.myabc.ui.organisms.HeaderPage
import com.outivox.myabc.ui.organisms.MenuResourceItemAttribute
import com.outivox.myabc.ui.organisms.PrimaryButton
import com.outivox.myabc.ui.organisms.ProfileComponent
import com.outivox.myabc.ui.organisms.ProfileComponentAttribute
import com.outivox.myabc.ui.organisms.SettingMenuSection
import com.outivox.myabc.ui.organisms.SettingMenuSectionAttribute

// TODO (Step 2.3): Expose SettingScreen UDF contracts and handle menu clicks.
// State flows down into SettingScreenContent; user events flow up via SettingScreenEvent.
data class SettingScreenState(
    @DrawableRes val userAvatar: Int = 0,
    val userName: String = "",
    val userEmail: String = "",
    val settingMenuList: List<SettingMenuSectionAttribute> = emptyList(),
)

sealed class SettingScreenEvent {
    data object OnBackPressed : SettingScreenEvent()
    data object OnLogoutClicked : SettingScreenEvent()
    data class OnSettingItemClicked(val title: String) : SettingScreenEvent()
}

@Composable
fun SettingScreen(
    state: SettingScreenState = SettingScreenState(),
    event: (SettingScreenEvent) -> Unit = {},
) {
    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        containerColor = Color.White,
        topBar = {
            HeaderPage(
                title = "Settings",
                leadingIconRes = R.drawable.ic_arrow_left,
                onClickLeadingIcon = {
                    event(SettingScreenEvent.OnBackPressed)
                },
            )
        },
        content = {
            SettingScreenContent(
                modifier = Modifier.padding(it),
                state = state,
                onItemClick = { title ->
                    event(SettingScreenEvent.OnSettingItemClicked(title))
                },
            )
        },
        bottomBar = {
            PrimaryButton(
                modifier = Modifier.padding(16.dp),
                text = "Logout",
                leadingIconRes = R.drawable.ic_logout_outline,
                onClick = {
                    event(SettingScreenEvent.OnLogoutClicked)
                }
            )
        },
    )
    BackHandler {
        event(SettingScreenEvent.OnBackPressed)
    }
}

@Composable
private fun SettingScreenContent(
    modifier: Modifier,
    state: SettingScreenState,
    onItemClick: (String) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        item {
            ProfileComponent(
                attribute = ProfileComponentAttribute(
                    avatarImageRes = state.userAvatar,
                    userName = state.userName,
                    userEmail = state.userEmail,
                ),
            )
        }

        items(state.settingMenuList) { section ->
            SettingMenuSection(
                attribute = section,
                onItemClick = onItemClick,
            )
        }
    }
}

@Preview
@Composable
private fun SettingScreenPreview() {
    SettingScreen(
        state = SettingScreenState(
            userAvatar = R.drawable.img_man_side_view,
            userName = "Alex Doe",
            userEmail = "alex.doe@email.com",
            settingMenuList = listOf(
                SettingMenuSectionAttribute(
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
                SettingMenuSectionAttribute(
                    title = "SECURITY",
                    itemList = listOf(
                        MenuResourceItemAttribute(
                            iconRes = R.drawable.ic_setting,
                            label = "Change Password",
                        ),
                        MenuResourceItemAttribute(
                            iconRes = R.drawable.ic_setting,
                            label = "Manage Biometrics",
                        ),
                        MenuResourceItemAttribute(
                            iconRes = R.drawable.ic_setting,
                            label = "Linked Accounts & Devices",
                        ),
                    ),
                ),
                SettingMenuSectionAttribute(
                    title = "APP PREFERENCES",
                    itemList = listOf(
                        MenuResourceItemAttribute(
                            iconRes = R.drawable.ic_setting,
                            label = "Notification",
                        ),
                        MenuResourceItemAttribute(
                            iconRes = R.drawable.ic_setting,
                            label = "Theme",
                        ),
                    ),
                ),
            ),
        ),
    )
}