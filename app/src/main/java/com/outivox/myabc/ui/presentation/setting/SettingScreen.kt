package com.outivox.myabc.ui.presentation.setting

import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.outivox.myabc.R
import com.outivox.myabc.databinding.LayoutSettingScreenBinding
import com.outivox.myabc.ui.organisms.HeaderPage
import com.outivox.myabc.ui.organisms.MenuResourceItemAttribute
import com.outivox.myabc.ui.organisms.PrimaryButton
import com.outivox.myabc.ui.organisms.ProfileComponent
import com.outivox.myabc.ui.organisms.ProfileComponentAttribute
import com.outivox.myabc.ui.organisms.SettingMenuSection
import com.outivox.myabc.ui.organisms.SettingMenuSectionAttribute

data class SettingScreenState(
    @DrawableRes val userAvatar: Int = 0,
    val userName: String = "",
    val userEmail: String = "",
    val settingMenuList: List<SettingMenuSectionAttribute> = emptyList(),
)

sealed class SettingScreenEvent {
    data object OnBackPressed : SettingScreenEvent()
    data object OnLogoutClicked : SettingScreenEvent()
}

/**
 * SettingScreen: Demonstrates XML-to-Compose Incremental Migration.
 *
 * Migration Roadmap:
 * - Step 1.3: In layout_setting_screen.xml & SettingScreen, migrate Top Bar and Bottom Bar to ComposeView slots
 *             using HeaderPage and PrimaryButton with ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed.
 * - Step 2.2: Migrate Profile and Menu Sections to ComposeView slots using ProfileComponent and SettingMenuSection.
 * - Step 3.1: Replace AndroidViewBinding with pure Jetpack Compose Scaffold & LazyColumn.
 * - Step 3.2: Implement SettingScreenContent using LazyColumn.
 * - Step 3.3: Add @Preview SettingScreenPreview.
 */
@Composable
fun SettingScreen(
    state: SettingScreenState = SettingScreenState(),
    event: (SettingScreenEvent) -> Unit = {},
) {
    val menuAdapter = remember { SettingMenuAdapter() }

    // TODO (Step 3.1): Replace AndroidViewBinding with pure Jetpack Compose Scaffold:
    // Scaffold(
    //     modifier = Modifier.systemBarsPadding(),
    //     containerColor = Color.White,
    //     topBar = {
    //         HeaderPage(
    //             title = "Settings",
    //             leadingIconRes = R.drawable.ic_arrow_left,
    //             onClickLeadingIcon = { event(SettingScreenEvent.OnBackPressed) }
    //         )
    //     },
    //     content = {
    //         SettingScreenContent(
    //             modifier = Modifier.padding(it),
    //             state = state,
    //         )
    //     },
    //     bottomBar = {
    //         PrimaryButton(
    //             modifier = Modifier.padding(16.dp),
    //             text = "Logout",
    //             leadingIconRes = R.drawable.ic_logout_outline,
    //             onClick = { event(SettingScreenEvent.OnLogoutClicked) }
    //         )
    //     }
    // )

    AndroidViewBinding(
        factory = LayoutSettingScreenBinding::inflate,
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
    ) {
        // Bind legacy XML Top Bar
        ivBack.setOnClickListener {
            event(SettingScreenEvent.OnBackPressed)
        }

        // Bind legacy XML Profile Section
        if (state.userAvatar != 0) {
            ivAvatar.setImageResource(state.userAvatar)
        }
        tvUserName.text = state.userName
        tvUserEmail.text = state.userEmail

        // Bind legacy XML RecyclerView for Menu Sections
        if (rvSettingMenu.adapter == null) {
            rvSettingMenu.layoutManager = LinearLayoutManager(root.context)
            rvSettingMenu.adapter = menuAdapter
            menuAdapter.submitList(state.settingMenuList)
        }

        // Bind legacy XML Logout Button
        btnLogout.setOnClickListener {
            event(SettingScreenEvent.OnLogoutClicked)
        }

        // =========================================================================
        // TODO (Step 1.3): Bottom-Up Interop - Migrate Header & Bottom Bar to ComposeView:
        // xmlHeaderContainer.visibility = android.view.View.GONE
        // composeHeader.apply {
        //     visibility = android.view.View.VISIBLE
        //     setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        //     setContent {
        //         HeaderPage(
        //             title = "Settings",
        //             leadingIconRes = R.drawable.ic_arrow_left,
        //             onClickLeadingIcon = { event(SettingScreenEvent.OnBackPressed) }
        //         )
        //     }
        // }
        // xmlBottomBarContainer.visibility = android.view.View.GONE
        // composeBottomBar.apply {
        //     visibility = android.view.View.VISIBLE
        //     setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        //     setContent {
        //         PrimaryButton(
        //             modifier = Modifier.padding(16.dp),
        //             text = "Logout",
        //             leadingIconRes = R.drawable.ic_logout_outline,
        //             onClick = { event(SettingScreenEvent.OnLogoutClicked) }
        //         )
        //     }
        // }
        // =========================================================================

        // =========================================================================
        // TODO (Step 2.2): Bottom-Up Interop - Migrate Profile & Menu Sections to ComposeView:
        // xmlProfileContainer.visibility = android.view.View.GONE
        // composeProfile.apply {
        //     visibility = android.view.View.VISIBLE
        //     setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        //     setContent {
        //         ProfileComponent(
        //             attribute = ProfileComponentAttribute(
        //                 imageRes = state.userAvatar,
        //                 name = state.userName,
        //                 email = state.userEmail,
        //             )
        //         )
        //     }
        // }
        // rvSettingMenu.visibility = android.view.View.GONE
        // composeMenuSections.apply {
        //     visibility = android.view.View.VISIBLE
        //     setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        //     setContent {
        //         state.settingMenuList.forEach { section ->
        //             SettingMenuSection(attribute = section)
        //         }
        //     }
        // }
        // =========================================================================
    }

    BackHandler {
        event(SettingScreenEvent.OnBackPressed)
    }
}

// TODO (Step 3.2): Implement pure Compose SettingScreenContent with LazyColumn
@Composable
fun SettingScreenContent(modifier: Modifier = Modifier, state: SettingScreenState) {
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
            SettingMenuSection(attribute = section)
        }
    }
}

// TODO (Step 3.3): Add Android Studio Design Preview for SettingScreen
@Preview
@Composable
fun SettingScreenPreview() {
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