/**
 * Created by ThuanPx on 3/15/20.
 * Source: https://docs.gradle.org/current/userguide/kotlin_dsl.html
 * Versions in project
 */
object Versions {
    const val kotlin = "1.3.72"
    const val android_gradle_plugin = "4.0.0"

    const val compile_sdk_version = 29
    const val build_tools_version = "29.0.2"
    const val min_sdk_version = 21
    const val target_sdk_version = 29

    const val appcompat = "1.1.0"
    const val core_ktx = "1.1.0"
    const val ktx = "2.2.0"
    const val constraint_layout = "1.1.3"
    const val material = "1.2.0-beta01"

    const val room_version = "2.2.5"
    const val ktlint = "0.36.0"
    const val detekt = "1.9.1"
    const val koin = "2.1.5"
    const val rxjava = "3.0.0"
    const val retrofit = "2.7.1"
    const val retrofit_adapter_rxjava3 = "3.0.0-RC8"
    const val okhttp = "4.7.2"
    const val glide = "4.8.0"
    const val coroutines = "1.3.8"
    const val timber = "4.7.1"
    const val leak_canary = "2.0"
    const val pusher = "2.0.2"
    const val lottie = "3.3.1"
    const val kt_ext = "1.0.0"
    const val conscrypt_android = "2.4.0"
    const val hilt = "2.28-alpha"
    const val hilt_view_model = "1.0.0-alpha01"
}

/**
 * Gradle and kotlin in project(Project: project)
 */
object ClassPaths {
    const val android_gradle_plugin =
        "com.android.tools.build:gradle:${Versions.android_gradle_plugin}"
    const val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val hilt_gradle_plugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
}

/**
 * Plugin in project(Module: app)
 */
object Plugins {
    const val androidApp = "com.android.application"
    const val kotlinAndroid = "android"
    const val kotlin = "kotlin"
    const val kotlinExt = "android.extensions"
    const val kotlinApt = "kapt"
    const val hilt = "dagger.hilt.android.plugin"
    const val detekt = "io.gitlab.arturbosch.detekt"
    const val koin = "koin"
}

/**
 * Dependency in project
 */
object Dependencies {

    const val support_app_compat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val support_design = "com.google.android.material:material:${Versions.material}"
    const val support_core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
    const val support_core = "androidx.core:core:${Versions.core_ktx}"
    const val view_model_ktx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.ktx}"
    const val live_data_ktx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.ktx}"

    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val constraint_layout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}"

    const val ktlint = "com.pinterest:ktlint:${Versions.ktlint}"
    const val ktlint_core = "com.pinterest:ktlint:ktlint-core:${Versions.ktlint}"

    const val koin_view_model = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
    const val koin_ext = "org.koin:koin-androidx-ext:${Versions.koin}"

    const val rx_java = "io.reactivex.rxjava3:rxjava:${Versions.rxjava}"
    const val rx_android = "io.reactivex.rxjava3:rxandroid:${Versions.rxjava}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofit_converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val retrofit_adapter_rxjava =
        "com.github.akarnokd:rxjava3-retrofit-adapter:${Versions.retrofit_adapter_rxjava3}"

    const val ok_http = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val ok_http_logging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"

    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glide_compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"

    const val coroutines_android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val coroutines_core =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    const val leak_canary = "com.squareup.leakcanary:leakcanary-android:${Versions.leak_canary}"

    const val room_runtime = "androidx.room:room-runtime:${Versions.room_version}"
    const val room_compiler = "androidx.room:room-compiler:${Versions.room_version}"
    const val room_ktx = "androidx.room:room-ktx:${Versions.room_version}"

    const val pusher = "com.pusher:pusher-java-client:${Versions.pusher}"

    const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"

    const val kt_ext = "com.github.ThuanPx:KtExt:${Versions.kt_ext}"

    const val conscrypt_android = "org.conscrypt:conscrypt-android:${Versions.conscrypt_android}"

    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hilt_compiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val hilt_view_model = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hilt_view_model}"
    const val hilt_androidx_compiler = "androidx.hilt:hilt-compiler:${Versions.hilt_view_model}"
}