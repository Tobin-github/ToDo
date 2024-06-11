plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.dagger.hilt.android)
}

android {
    namespace = "top.tobin.common"
    compileSdk = rootProject.properties["sdk_compile_version"] as Int

    defaultConfig {
        minSdk = rootProject.extra["sdk_min_version"] as Int
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

//        consumerProguardFiles("consumer-rules.pro")

        ndk {
            abiFilters.add("armeabi-v7a")
            abiFilters.add("arm64-v8a")
            abiFilters.add("x86_64")
            abiFilters.add("x86")
        }
    }

    buildTypes {

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        encoding = "UTF-8"
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {
    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    api(libs.bundles.androidx)
    api(libs.material)

    api(libs.kotlin.reflect)
    api(libs.gson)
    api(libs.glide)

    // hilt https://dagger.dev/hilt/gradle-setup
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    /**
     * coroutines
     */
    api(libs.kotlinx.coroutines.core)
    api(libs.kotlinx.coroutines.android)

    /**
     * Retrofit
     */
    api(libs.retrofit2)
    api(libs.retrofit2.converter.gson)
    api(libs.retrofit2.mock)
    api(libs.okhttp3.logging.interceptor)

    /**
     * 三方库
     */
    // AutoSize 自动适配屏幕
    api(libs.android.auto.size)
    // https://github.com/airbnb/lottie-android
    api(libs.lottie)
    // https://github.com/gyf-dev/ImmersionBar
    api(libs.immersionbar)
    api(libs.immersionbar.ktx)
    api(libs.mmkv)

}