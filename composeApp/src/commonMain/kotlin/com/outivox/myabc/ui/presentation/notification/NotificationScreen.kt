package com.outivox.myabc.ui.presentation.notification

import androidx.activity.compose.BackHandler
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
import com.outivox.myabc.core.util.LocalNavBackStack
import com.outivox.myabc.ui.organisms.HeaderPage
import com.outivox.myabc.ui.organisms.NotificationResourceItemAttribute
import com.outivox.myabc.ui.organisms.NotificationSection
import com.outivox.myabc.ui.organisms.NotificationSectionAttribute

data class NotificationScreenState(
    val notificationList: List<NotificationSectionAttribute> = emptyList(),
)

sealed class NotificationScreenEvent {
    data object OnBackPressed : NotificationScreenEvent()
}

@Composable
fun NotificationScreen(
    state: NotificationScreenState = NotificationScreenState(),
) {
    val navBackStack = LocalNavBackStack.current

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        containerColor = Color.White,
        topBar = {
            HeaderPage(
                title = "Notifications",
                leadingIconRes = R.drawable.ic_arrow_left,
                onClickLeadingIcon = {
                    navBackStack.removeLastOrNull()
                },
            )
        },
        content = {
            NotificationScreenContent(
                modifier = padding(it),
                state = state,
                event = { screenEvent ->
                    when (screenEvent) {
                        NotificationScreenEvent.OnBackPressed -> {
                            navBackStack.removeLastOrNull()
                        }
                    }
                },
            )
        },
    )
}


@Composable
private fun NotificationScreenContent(
    modifier: Modifier,
    state: NotificationScreenState,
    event: (NotificationScreenEvent) -> Unit,
) {
    BackHandler {
        event(NotificationScreenEvent.OnBackPressed)
    }
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
    ) {
        items(state.notificationList) { section ->
            NotificationSection(attribute = section)
        }
    }
}

@Preview
@Composable
private fun NotificationScreenPreview() {
    NotificationScreen(
        state = NotificationScreenState(
            notificationList = listOf(
                NotificationSectionAttribute(
                    date = "TODAY",
                    itemList = listOf(
                        NotificationResourceItemAttribute(
                            iconRes = R.drawable.ic_card_outline,
                            label = "Card Purchase",
                            description = "$50.00 at Starbucks",
                            timestamp = "10:45 AM",
                        ),
                        NotificationResourceItemAttribute(
                            iconRes = R.drawable.ic_card_outline,
                            label = "New Device Login",
                            description = "From a device in London, UK",
                            timestamp = "9:30 AM",
                        ),
                    ),
                ),
                NotificationSectionAttribute(
                    date = "YESTERDAY",
                    itemList = listOf(
                        NotificationResourceItemAttribute(
                            iconRes = R.drawable.ic_card_outline,
                            label = "New Cashback Offer",
                            description = "Earn 5% back on dining.",
                            timestamp = "4:15 PM",
                        ),
                        NotificationResourceItemAttribute(
                            iconRes = R.drawable.ic_card_outline,
                            label = "Your e-Statement is Ready",
                            description = "Statement for October 2023",
                            timestamp = "9:02 AM",
                            isUnread = false,
                        ),
                    ),
                ),
                NotificationSectionAttribute(
                    date = "OCTOBER 15",
                    itemList = listOf(
                        NotificationResourceItemAttribute(
                            iconRes = R.drawable.ic_card_outline,
                            label = "Card Purchase",
                            description = "$12.50 at Starbucks",
                            timestamp = "11:55 AM",
                            isUnread = false,
                        ),
                    ),
                ),
            ),
        ),
    )
}