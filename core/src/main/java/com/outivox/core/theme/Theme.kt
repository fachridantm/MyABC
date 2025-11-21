package com.outivox.core.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.outivox.core.bottomsheet.BottomSheetFooter
import com.outivox.core.bottomsheet.BottomSheetHeader
import com.outivox.core.component.BottomSheetFooterState
import com.outivox.core.component.BottomSheetHeaderState
import com.outivox.core.component.DragHandleBottomSheet

private val DarkColorScheme = darkColorScheme(
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

private val LightColorScheme = lightColorScheme(
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

@Composable
fun MyABCTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme =
        when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }

            darkTheme -> DarkColorScheme
            else -> LightColorScheme
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyABCBottomSheetTheme(
    headerState: BottomSheetHeaderState,
    footerState: BottomSheetFooterState,
    content: @Composable () -> Unit,
) {
    MyABCTheme {
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