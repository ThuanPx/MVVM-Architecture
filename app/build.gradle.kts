import java.text.SimpleDateFormat
import java.util.Calendar
import kotlin.collections.List

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
    apply(from = "../ktlint-custom-rules/ktlint.gradle.kts")
    apply(from = "../autodimension.gradle.kts")
}

android {
    compileSdk = 32
    buildToolsVersion = "30.0.3"
    flavorDimensions += "default"

    defaultConfig {
        applicationId = "com.thuanpx.mvvm_architecture"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0.0"
        vectorDrawables.useSupportLibrary = true
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
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                file("proguard-rules.pro")
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    buildFeatures {
        viewBinding = true
    }

    packagingOptions {
        resources.excludes.add("META-INF/DEPENDENCIES")
        resources.excludes.add("META-INF/LICENSE")
        resources.excludes.add("META-INF/LICENSE.txt")
        resources.excludes.add("META-INF/license.txt")
        resources.excludes.add("META-INF/NOTICE")
        resources.excludes.add("META-INF/NOTICE.txt")
        resources.excludes.add("META-INF/notice.txt")
        resources.excludes.add("META-INF/ASL2.0")
        resources.excludes.add("META-INF/*.kotlin_module")
    }

    applicationVariants.all {
        val outputFileName = name +
            "_versionName_${versionName}" +
            "_versionCode_${versionCode}" +
            "_time_${SimpleDateFormat("HH_mm_dd_MM_yyyy").format(Calendar.getInstance().time)}.apk"
        outputs.all {
            val output = this as? com.android.build.gradle.internal.api.BaseVariantOutputImpl
            output?.outputFileName = outputFileName
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlinx.coroutines.FlowPreview" + "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
    }
}

kapt {
    useBuildCache = true
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    // Kotlin Extension
    implementation(project((":KtExt")))
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.6.10")
    // App compat & design
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    // support library
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // Okhttp
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
    // Glide
    implementation("com.github.bumptech.glide:glide:4.13.0")
    implementation("com.github.florent37:glidepalette:2.1.2")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.4.1")
    annotationProcessor("com.github.bumptech.glide:compiler:4.13.0")
    kapt("com.github.bumptech.glide:compiler:4.13.0")
    // Gson
    implementation("com.google.code.gson:gson:2.9.0")
    // Leak canary
    // debugImplementation("com.squareup.leakcanary:leakcanary-android:2.0")
    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")
    // KTX
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.fragment:fragment-ktx:1.4.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")
    implementation("androidx.activity:activity-ktx:1.6.0-alpha01")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.0-alpha05")
    // Hilt
    implementation("com.google.dagger:hilt-android:2.40.5")
    kapt("com.google.dagger:hilt-android-compiler:2.40")
    // Lottie
    implementation("com.airbnb.android:lottie:3.6.1")
    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    // Bundler
    implementation("com.github.skydoves:bundler:1.0.4")
    // Paging
    implementation("androidx.paging:paging-runtime-ktx:3.1.1")
}
