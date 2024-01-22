import java.text.SimpleDateFormat
import java.util.Calendar

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.thuanpx.view_mvvm_architecture"
    compileSdk = 34
    flavorDimensions += "default"

    defaultConfig {
        applicationId = "com.thuanpx.view_mvvm_architecture"
        minSdk = 25
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    productFlavors {
        create("dev") {
            applicationIdSuffix = ".dev"
            versionCode = 1
            versionName = "1.0.0"

            buildConfigField("String", "END_POINT", "\"https://pokeapi.co/api/v2/\"")
        }

        create("prod") {
            versionCode = 1
            versionName = "1.0.0"

            buildConfigField("String", "END_POINT", "\"https://pokeapi.co/api/v2/\"")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                file("proguard-rules.pro")
            )
            proguardFile("proguard/proguard-google-play-services.pro")
            proguardFile("proguard/proguard-square-okhttp.pro")
            proguardFile("proguard/proguard-square-retrofit.pro")
            proguardFile("proguard/proguard-google-analytics.pro")
            proguardFile("proguard/proguard-facebook.pro")
            proguardFile("proguard/proguard-project.pro")
            proguardFile("proguard/proguard-hilt.pro")
            proguardFile("proguard/proguard-support-v7-appcompat.pro")
            proguardFile("proguard/okhttp3.pro")
            proguardFile("proguard/kotlin.pro")
            proguardFile("proguard/retrofit2.pro")
            proguardFile("proguard/proguard-testfairy.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    applicationVariants.all {
        val outputFileName = name +
                "_versionName_$versionName" +
                "_versionCode_$versionCode" +
                "_time_${SimpleDateFormat("HH_mm_dd_MM_yyyy").format(Calendar.getInstance().time)}.apk"
        outputs.all {
            val output = this as? com.android.build.gradle.internal.api.BaseVariantOutputImpl
            output?.outputFileName = outputFileName
        }
    }

}

kapt {
    useBuildCache = true
    correctErrorTypes = true
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    // App compat & design
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    // Okhttp
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.3")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.3")
    // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.15.1")
    kapt("com.github.bumptech.glide:compiler:4.15.1")
    // Gson
    implementation("com.google.code.gson:gson:2.10.1")
    // Moshi
    implementation("com.squareup.moshi:moshi-kotlin:1.15.0")
    // Leak canary
    // debugImplementation("com.squareup.leakcanary:leakcanary-android:2.0")
    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")
    // KTX
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    // Hilt
    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-android-compiler:2.50")
    // Lottie
    implementation("com.airbnb.android:lottie:6.1.0")
    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    // Paging
    implementation("androidx.paging:paging-runtime-ktx:3.2.1")
}