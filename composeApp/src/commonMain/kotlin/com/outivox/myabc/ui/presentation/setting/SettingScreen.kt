package com.outivox.myabc.ui.presentation.setting

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
import androidx.navigationevent.NavigationEventInfo
import androidx.navigationevent.compose.NavigationBackHandler
import androidx.navigationevent.compose.rememberNavigationEventState
import com.outivox.myabc.generated.resources.Res
import com.outivox.myabc.generated.resources.ic_arrow_left
import com.outivox.myabc.generated.resources.ic_logout_outline
import com.outivox.myabc.generated.resources.ic_setting
import com.outivox.myabc.generated.resources.img_man_side_view
import com.outivox.myabc.ui.organisms.HeaderPage
import com.outivox.myabc.ui.organisms.MenuResourceItemAttribute
import com.outivox.myabc.ui.organisms.PrimaryButton
import com.outivox.myabc.ui.organisms.ProfileComponent
import com.outivox.myabc.ui.organisms.ProfileComponentAttribute
import com.outivox.myabc.ui.organisms.SettingMenuSection
import com.outivox.myabc.ui.organisms.SettingMenuSectionAttribute
import org.jetbrains.compose.resources.DrawableResource

private data class SettingScreenState(
    val userAvatar: DrawableResource? = null,
    val userName: String = "",
    val userEmail: String = "",
    val settingMenuList: List<SettingMenuSectionAttribute> = emptyList(),
)

private sealed class SettingScreenEvent {
    data object OnBackPressed : SettingScreenEvent()
    data object OnLogoutClicked : SettingScreenEvent()
}

@Composable
private fun SettingScreen(
    state: SettingScreenState = SettingScreenState(),
    event: (SettingScreenEvent) -> Unit = {},
) {
    val navigationEventState = rememberNavigationEventState(
        currentInfo = NavigationEventInfo.None
    )

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        containerColor = Color.White,
        topBar = {
            HeaderPage(
                title = "Settings",
                leadingIconRes = Res.drawable.ic_arrow_left,
                onClickLeadingIcon = {
                    event(SettingScreenEvent.OnBackPressed)
                },
            )
        },
        content = {
            SettingScreenContent(
                modifier = Modifier.padding(it),
                state = state,
            )
        },
        bottomBar = {
            PrimaryButton(
                modifier = Modifier.padding(16.dp),
                text = "Logout",
                leadingIconRes = Res.drawable.ic_logout_outline,
                onClick = {
                    event(SettingScreenEvent.OnLogoutClicked)
                }
            )
        },
    )
    NavigationBackHandler(
        state = navigationEventState,
    ) {
        event(SettingScreenEvent.OnBackPressed)
    }
}

@Composable
private fun SettingScreenContent(modifier: Modifier, state: SettingScreenState) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        item {
            state.userAvatar?.let {
                ProfileComponent(
                    attribute = ProfileComponentAttribute(
                        avatarImageRes = state.userAvatar,
                        userName = state.userName,
                        userEmail = state.userEmail,
                    ),
                )
            }
        }

        items(state.settingMenuList) { section ->
            SettingMenuSection(attribute = section)
        }
    }
}

@Preview
@Composable
private fun SettingScreenPreview() {
    SettingScreen(
        state = SettingScreenState(
            userAvatar = Res.drawable.img_man_side_view,
            userName = "Alex Doe",
            userEmail = "alex.doe@email.com",
            settingMenuList = listOf(
                SettingMenuSectionAttribute(
                    title = "PERSONAL INFORMATION",
                    itemList = listOf(
                        MenuResourceItemAttribute(
                            iconRes = Res.drawable.ic_setting,
                            label = "Contact Details",
                            description = "Manage your phone and email",
                        ),
                        MenuResourceItemAttribute(
                            iconRes = Res.drawable.ic_setting,
                            label = "Mailing Address",
                        ),
                    ),
                ),
                SettingMenuSectionAttribute(
                    title = "SECURITY",
                    itemList = listOf(
                        MenuResourceItemAttribute(
                            iconRes = Res.drawable.ic_setting,
                            label = "Change Password",
                        ),
                        MenuResourceItemAttribute(
                            iconRes = Res.drawable.ic_setting,
                            label = "Manage Biometrics",
                        ),
                        MenuResourceItemAttribute(
                            iconRes = Res.drawable.ic_setting,
                            label = "Linked Accounts & Devices",
                        ),
                    ),
                ),
                SettingMenuSectionAttribute(
                    title = "APP PREFERENCES",
                    itemList = listOf(
                        MenuResourceItemAttribute(
                            iconRes = Res.drawable.ic_setting,
                            label = "Notification",
                        ),
                        MenuResourceItemAttribute(
                            iconRes = Res.drawable.ic_setting,
                            label = "Theme",
                        ),
                    ),
                ),
            ),
        ),
    )
}