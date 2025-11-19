import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    // Android
    alias(libs.plugins.android.library)
    // Kotlin
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.devtools.ksp)
    // Dagger Hilt
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "com.outivox.core"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    // Android
    api(libs.androidx.appcompat)
    api(libs.androidx.core.ktx)
    api(libs.androidx.core.splashscreen)

    // Kotlin Coroutines
    api(libs.kotlinx.coroutines.android)
    api(libs.kotlinx.coroutines.play.services)

    // Kotlin Serialization
    api(libs.kotlinx.serialization.json)
    api(libs.kotlinx.serialization.core)

    // Android Work
    api(libs.androidx.work.runtime.ktx)

    // Android Lifecycle
    api(libs.androidx.lifecycle.viewmodel.ktx)
    api(libs.androidx.lifecycle.livedata.ktx)
    api(libs.androidx.lifecycle.runtime.ktx)

    // Android Lifecycle Compose
    api(libs.androidx.lifecycle.runtime.compose)
    api(libs.androidx.lifecycle.viewmodel.compose)

    // Android Lifecycle Compose Navigation 3
    api(libs.androidx.lifecycle.viewmodel.navigation3)

    // Android Material
    api(libs.androidx.material)
    api(libs.androidx.material.icons)

    // Dagger Hilt
    api(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.android.compiler.ksp)

    // Android Hilt
    api(libs.androidx.hilt.navigation.compose)
    api(libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.compiler)

    // Compose Activity
    api(libs.androidx.activity.compose)

    // Compose Core
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.compose)
    api(libs.androidx.compose.constraint.layout)
    api(libs.androidx.compose.graphics)
    api(libs.androidx.compose.viewbinding)
    api(libs.androidx.compose.foundation.layout)
    debugApi(libs.androidx.compose.tooling)
    debugApi(libs.androidx.compose.tooling.preview)

    // Compose Material 3
    api(libs.androidx.material3)
    api(libs.androidx.material3.windowsizeclass)

    // Compose Testing
    testApi(libs.androidx.compose.test.manifest)
    testApi(libs.androidx.compose.test.junit4)

    // Compose Navigation
    api(libs.androidx.navigation.compose)
    api(libs.androidx.navigation.ui.ktx)
    api(libs.androidx.navigation.fragment.ktx)
    // Compose Navigation 3
    api(libs.androidx.navigation3.ui)
    api(libs.androidx.navigation3.runtime)
}