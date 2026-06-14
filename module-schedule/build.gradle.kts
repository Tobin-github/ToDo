plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.dagger.hilt.android)
}

android {
    namespace = "top.tobin.schedule"
    compileSdk = rootProject.properties["sdk_compile_version"] as Int

    defaultConfig {
        minSdk = rootProject.extra["sdk_min_version"] as Int

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        encoding = "UTF-8"
        sourceCompatibility = JavaVersion.toVersion(rootProject.extra["java_version"] as Int)
        targetCompatibility = JavaVersion.toVersion(rootProject.extra["java_version"] as Int)
    }

    kotlinOptions {
        jvmTarget = (rootProject.extra["java_version"] as Int).toString()
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":common-lib"))
    implementation(project(":navigation-tab"))
    implementation(project(":component-service"))
    implementation(project(":shared"))

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // hilt https://dagger.dev/hilt/gradle-setup
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
}