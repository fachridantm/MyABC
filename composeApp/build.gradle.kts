import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    // Android
    alias(libs.plugins.android.application)
    // Kotlin
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.devtools.ksp)
    // Compose
    alias(libs.plugins.compose.multiplatform)
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.outivox.myabc.generated.resources"
}

android {
    namespace = "com.outivox.myabc"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.outivox.myabc"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-DEBUG"
        }

        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
            freeCompilerArgs.addAll(
                listOf(
                    "-Xexpect-actual-classes",
                )
            )
        }
    }

    val xcfName = "composeApp"

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
            implementation(project(":authentication"))

            // Compose Resources
            implementation(libs.jetbrains.compose.resources)
        }
        commonTest.dependencies {
            implementation(project(":core"))
        }
        androidMain.dependencies {
            implementation(project(":core"))
        }
        iosMain.dependencies {
            implementation(project(":core"))
        }
    }
}
