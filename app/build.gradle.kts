import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    // Android
    alias(libs.plugins.android.application)
    // Kotlin
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.devtools.ksp)
    // Dagger Hilt
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "com.outivox.myabc"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.outivox.myabc"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
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
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

dependencies {
    // Module
    implementation(project(":core"))
    implementation(project(":authentication"))

    // Dagger Hilt
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.android.compiler.ksp)

    // Android Hilt
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.compiler)
}
