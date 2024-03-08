plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.ibashkimi.appinfo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ibashkimi.appinfo"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-dev"
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-opt-in=androidx.lifecycle.compose.ExperimentalLifecycleComposeApi",
        )
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get().toString()
    }

}

dependencies {
    coreLibraryDesugaring(libs.desugarJdk)
    implementation(platform(libs.compose.bom))

    implementation(libs.accompanist.drawablepainter)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.google.material)
    implementation(libs.androidx.activityCompose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core)
    implementation(libs.compose.ui.ui)
    implementation(libs.compose.ui.tooling)
    implementation(libs.compose.material.icons.extended)
    implementation(libs.compose.material3)
    implementation(libs.kotlinx.collectionsImmutable)

    implementation(libs.hilt.navigationCompose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.lifecycle.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.coilCompose)

    implementation(libs.hilt.android)
    kapt(libs.hilt.coreCompiler)

    implementation(libs.timber)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.compose.ui.test)
}
