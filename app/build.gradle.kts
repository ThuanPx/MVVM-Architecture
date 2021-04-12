import java.text.SimpleDateFormat
import java.util.Calendar

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
    id("kotlin-android")
    id("kotlin-parcelize")
}

buildscript {
    apply(from = "../buildSrc/ktlint.gradle.kts")
}

android {
    compileSdkVersion(30)
    buildToolsVersion("29.0.3")

    flavorDimensions("default")

    defaultConfig {
        applicationId = "com.thuanpx.mvvm_architecture"
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1_0"
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        setProperty(
            "archivesBaseName",
            "App-${SimpleDateFormat("HH_mm_dd_MM_yyyy").format(Calendar.getInstance().time)}-$versionName"
        )
    }

    productFlavors {
        create("DEV") {
            applicationIdSuffix = ".dev"
            versionCode = 1
            versionName = "1.0.0"

            buildConfigField("String", "END_POINT", "\"https://pokeapi.co/api/v2/\"")
        }

        create("PROD") {
            versionCode = 1
            versionName = "1.0.0"

            buildConfigField("String", "END_POINT", "\"https://pokeapi.co/api/v2/\"")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"), file("proguard-rules.pro")
            )
        }
    }

    dexOptions {
        javaMaxHeapSize = "4g"
        preDexLibraries = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        viewBinding = true
    }

    sourceSets["main"].java.srcDir("src/main/kotlin")

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/LICENSE")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/license.txt")
        exclude("META-INF/NOTICE")
        exclude("META-INF/NOTICE.txt")
        exclude("META-INF/notice.txt")
        exclude("META-INF/ASL2.0")
        exclude("META-INF/*.kotlin_module")
    }
}

kapt {
    useBuildCache = true
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.4.21")
    // App compat & design
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    // support library
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.2")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // Okhttp
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
    // Glide
    implementation("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")
    kapt("com.github.bumptech.glide:compiler:4.12.0")
    // Leak canary
    // debugImplementation("com.squareup.leakcanary:leakcanary-android:2.0")
    // Timber
    implementation("com.jakewharton.timber:timber:4.7.1")
    // KTX
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.fragment:fragment-ktx:1.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.0")
    implementation("com.github.ThuanPx:KtExt:1.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.4")
    implementation("androidx.activity:activity-ktx:1.3.0-alpha04")
    // Hilt
    implementation("com.google.dagger:hilt-android:2.31.2-alpha")
    kapt("com.google.dagger:hilt-android-compiler:2.31.2-alpha")
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
    kapt("androidx.hilt:hilt-compiler:1.0.0-beta01")
    // Other
    implementation("com.airbnb.android:lottie:3.3.1")
    implementation("com.github.florent37:glidepalette:2.1.2")
}
