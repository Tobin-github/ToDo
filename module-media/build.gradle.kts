plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.dagger.hilt.android)
}

android {
    namespace = "top.tobin.media"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
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
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // hilt https://dagger.dev/hilt/gradle-setup
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    implementation(libs.androidx.media3.common)
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.media3.database)
    implementation(libs.androidx.media3.datasource)

}