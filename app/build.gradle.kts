plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "it.antonino.dinnerdecideradmin"
    compileSdk = 35

    defaultConfig {
        applicationId = "it.antonino.dinnerdecideradmin"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "baseUrl", "\"https://192.168.0.20:1938/\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "baseUrl", "\"https://192.168.0.20:1938/\"")
        }
        release {
            isMinifyEnabled = false
            buildConfigField("String", "baseUrl", "\"https://192.168.0.20:1938/\"")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.koin.android)
    implementation(libs.koin.core.viewmodel)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.gson)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}