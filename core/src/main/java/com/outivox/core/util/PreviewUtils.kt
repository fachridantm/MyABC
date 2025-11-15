package com.outivox.core.util

import androidx.compose.ui.tooling.preview.Preview

/**
 * Annotation used for setting a specific locale to "in" (Indonesian) during a Compose function preview.
 *
 * This annotation is typically applied to functions annotated with `@Preview`
 * to render a UI representation as it would appear under the Indonesian locale.
 *
 * Usage of this annotation allows developers to test and validate UI components efficiently
 * for internationalization and localization requirements specific to Indonesia.
 */
@Preview(
    name = "Indonesia",
    locale = "in",
)
annotation class IndonesiaLocalePreview

/**
 * Annotation for previewing composables in the English locale ("en").
 *
 * This annotation is used to indicate that a composable function should be
 * previewed with configurations set to the English locale. It helps in testing
 * and verifying UI layouts and text appearance specific to the English language.
 *
 * Parameters:
 * - `locale`: Specifies the locale for the preview. Default is set to "en" for English.
 */
@Preview(
    name = "English",
    locale = "en",
)
annotation class EnglishLocalePreview

@IndonesiaLocalePreview
@EnglishLocalePreview
annotation class LocalePreview