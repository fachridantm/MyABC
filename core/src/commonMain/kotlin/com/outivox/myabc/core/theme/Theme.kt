package com.outivox.myabc.core.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.outivox.myabc.core.bottomsheet.BottomSheetFooter
import com.outivox.myabc.core.bottomsheet.BottomSheetHeader
import com.outivox.myabc.core.component.BottomSheetFooterState
import com.outivox.myabc.core.component.BottomSheetHeaderState
import com.outivox.myabc.core.component.DragHandleBottomSheet

internal val DarkColorScheme = darkColorScheme(
    primary = colorPrimary,
    secondary = colorSecondary,
    tertiary = colorTertiary,
    primaryContainer = colorPrimaryContainer,
    secondaryContainer = colorSecondaryContainer,
    tertiaryContainer = colorTertiaryContainer,
    onPrimary = colorOnPrimary,
    onSecondary = colorOnSecondary,
    onTertiary = colorOnTertiary,
    background = colorBackground,
    onBackground = colorOnBackground,
)

internal val LightColorScheme = lightColorScheme(
    primary = colorPrimary,
    secondary = colorSecondary,
    tertiary = colorTertiary,
    primaryContainer = colorPrimaryContainer,
    secondaryContainer = colorSecondaryContainer,
    tertiaryContainer = colorTertiaryContainer,
    onPrimary = colorOnPrimary,
    onSecondary = colorOnSecondary,
    onTertiary = colorOnTertiary,
    background = colorBackground,
    onBackground = colorOnBackground,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyABCBottomSheetTheme(
    headerState: BottomSheetHeaderState,
    footerState: BottomSheetFooterState,
    content: @Composable () -> Unit,
) {
    MyABCTheme {
        Box(modifier = Modifier.fillMaxWidth()) {
            ModalBottomSheet(
                modifier = Modifier.systemBarsPadding(),
                sheetState = SheetState(
                    skipPartiallyExpanded = true,
                    initialValue = SheetValue.Expanded,
                    positionalThreshold = { 0f },
                    velocityThreshold = { 0f },
                ),
                onDismissRequest = {},
                containerColor = colorPrimaryContainer,
                dragHandle = { DragHandleBottomSheet() },
                shape = RoundedCornerShape(
                    topStart = 24.dp,
                    topEnd = 24.dp,
                ),
                content = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        // Parent of Header and Content
                        Column(
                            modifier = Modifier
                        ) {
                            // Header
                            BottomSheetHeader(headerState)

                            // Content
                            Column { content() }
                        }

                        // Footer
                        BottomSheetFooter(footerState)
                    }
                },
            )
        }
    }
}
