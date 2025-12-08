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
        namespace = "com.outivox.myabc.authentication"
        compileSdk = 36
    }

    val xcfName = "authentication"

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
            implementation(project(":core"))
        }
        androidMain.dependencies {
            implementation(project(":core"))
        }
    }
}
