package com.outivox.myabc.core.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.outivox.myabc.core.generated.resources.Res
import com.outivox.myabc.core.generated.resources.plus_jakarta_sans_regular
import org.jetbrains.compose.resources.Font

@Composable
fun Typography(): Typography {
    val plusJakartaSans = FontFamily(
        Font(Res.font.plus_jakarta_sans_regular)
    )
    return Typography(
        bodyLarge = TextStyle(
            fontFamily = plusJakartaSans,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
        ),
        titleLarge = TextStyle(
            fontFamily = plusJakartaSans,
            fontWeight = FontWeight.Normal,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.sp
        ),
        labelSmall = TextStyle(
            fontFamily = plusJakartaSans,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        )
    )
}
