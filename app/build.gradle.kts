plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.dagger.hilt.android)
}

android {
    namespace = "top.tobin.todo"
    compileSdk = rootProject.properties["sdk_compile_version"] as Int

    defaultConfig {
        applicationId = "top.tobin.todo"
        minSdk = rootProject.properties["sdk_min_version"] as Int
        targetSdk = rootProject.properties["sdk_target_version"] as Int
        versionCode = 1
        versionName = "1.0"

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

    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(project(":navigation-tab"))
    implementation(project(":component-service"))
    implementation(project(":shared"))
    implementation(project(":common-lib"))
    implementation(project(":module-accounting"))
    implementation(project(":module-gallery"))
    implementation(project(":module-media"))
    implementation(project(":module-schedule"))
    implementation(project(":module-my"))
    implementation(project(":framework-lib"))

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    debugImplementation(libs.leakcanary.android)

    implementation(libs.bundles.androidx)

    // hilt https://dagger.dev/hilt/gradle-setup
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // navigation
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)

    implementation(libs.bcpkix.jdk15on)
    implementation(libs.androidx.metrics.performance)

}