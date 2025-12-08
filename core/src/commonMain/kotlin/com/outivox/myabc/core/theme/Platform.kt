package com.outivox.myabc.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable

@Composable
expect fun MyABCTheme(
    darkTheme: Boolean= isSystemInDarkTheme(),
    dynamicColor: Boolean= true,
    content: @Composable () -> Unit,
)
