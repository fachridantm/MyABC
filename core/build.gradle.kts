plugins {
    // Android
    alias(libs.plugins.android.kotlin.multiplatform.library)
    // Kotlin
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.devtools.ksp)
    // Compose
    alias(libs.plugins.compose.multiplatform)
}

kotlin {
    androidLibrary {
        namespace = "com.outivox.myabc.core"
        compileSdk = 36
    }

    val xcfName = "core"

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = xcfName
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            // Kotlin Coroutines
            api(libs.kotlinx.coroutines.core)

            // Kotlin Serialization
            api(libs.kotlinx.serialization.json)
            api(libs.kotlinx.serialization.core)

            // Koin
            api(libs.koin.core)

            // Android Lifecycle
            api(libs.androidx.lifecycle.common)
            api(libs.androidx.lifecycle.runtime)
            api(libs.androidx.lifecycle.viewmodel)
            api(libs.androidx.lifecycle.viewmodel.compose)
            api(libs.androidx.lifecycle.viewmodel.navigation3)
            api(libs.androidx.lifecycle.viewmodel.savedstate)

            // Compose UI
            api(libs.jetbrains.compose.ui)

            // Compose UI Tooling Preview
            api(libs.jetbrains.compose.ui.tooling.preview)

            // Compose Runtime
            api(libs.jetbrains.compose.runtime)

            // Compose Foundation
            api(libs.jetbrains.compose.foundation)

            // Compose Foundation Layout
            api(libs.jetbrains.compose.foundation.layout)

            // Compose Material
            api(libs.jetbrains.material)

            // Compose Material Icons
            api(libs.jetbrains.material.icons.extended)

            // Compose Material 3
            api(libs.jetbrains.material3)

            // Compose Material 3 Window Size Class
            api(libs.jetbrains.material3.window.size)

            // Compose Material 3 Adaptive
            api(libs.jetbrains.material3.adaptive)

            // Jetbrains Compose Navigation
            api(libs.jetbrains.navigation.compose)

            // Jetbrains Navigation Runtime
            api(libs.jetbrains.navigation.runtime)

            // Jetbrains Navigation 3
            api(libs.jetbrains.navigation3.ui)

            // Jetbrains Saved State
            api(libs.jetbrains.androidx.savedstate.compose)

            // Jetbrains Compose Saved State
            api(libs.jetbrains.androidx.savedstate)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            // Kotlin Coroutines
            api(libs.kotlinx.coroutines.android)
            api(libs.kotlinx.coroutines.play.services)

            // Koin
            api(libs.koin.android)
            api(libs.koin.androidx.compose)
            api(libs.koin.androidx.workmanager)
            api(libs.koin.androidx.navigation)

            // Android
            api(libs.androidx.appcompat)
            api(libs.androidx.core.ktx)
            api(libs.androidx.core.splashscreen)

            // Android Work
            api(libs.androidx.work.runtime.ktx)

            // Compose Activity
            api(libs.androidx.activity.compose)

            // Compose UI Tooling
            api(libs.jetbrains.compose.ui.tooling)
        }

        iosMain.dependencies {
            // Compose UI Uikit
            api(libs.jetbrains.compose.ui.uikit)
        }
    }
}
