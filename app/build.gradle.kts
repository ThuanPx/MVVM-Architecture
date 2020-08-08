import java.text.SimpleDateFormat
import java.util.Calendar

plugins {
    id(Plugins.androidApp)
    kotlin(Plugins.kotlinAndroid)
    kotlin(Plugins.kotlinExt)
    kotlin(Plugins.kotlinApt)
    id(Plugins.hilt)
}

buildscript {
    apply(from = "../buildSrc/ktlint.gradle.kts")
}

android {
    compileSdkVersion(Versions.compile_sdk_version)
    buildToolsVersion(Versions.build_tools_version)

    flavorDimensions("default")

    defaultConfig {
        applicationId = "com.thuanpx.mvvm_architecture"
        minSdkVersion(Versions.min_sdk_version)
        targetSdkVersion(Versions.target_sdk_version)
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

            buildConfigField("String", "END_POINT", "\"https://api-dev.neo-lab.com/v1/\"")
        }

        create("PROD") {
            versionCode = 1
            versionName = "1.0.0"

            buildConfigField("String", "END_POINT", "\"https://api-dev.neo-lab.com/v1/\"")
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

androidExtensions {
    isExperimental = true
}

kapt {
    useBuildCache = true
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    // Kotlin
    implementation(Dependencies.kotlin_stdlib)
    // App compat & design
    implementation(Dependencies.support_app_compat)
    implementation(Dependencies.support_core)
    implementation(Dependencies.support_design)
    implementation(Dependencies.constraint_layout)
    // Coroutines
    implementation(Dependencies.coroutines_core)
    implementation(Dependencies.coroutines_android)
    // Room
    implementation(Dependencies.room_runtime)
    implementation(Dependencies.room_compiler)
    implementation(Dependencies.room_ktx)
    // Retrofit
    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofit_converter_gson)
    // Okhttp
    implementation(Dependencies.ok_http)
    implementation(Dependencies.ok_http_logging)
    // Glide
    implementation(Dependencies.glide)
    annotationProcessor(Dependencies.glide_compiler)
    kapt(Dependencies.glide_compiler)
    // Leak canary
    debugImplementation(Dependencies.leak_canary)
    // Timber
    implementation(Dependencies.timber)
    // KTX
    implementation(Dependencies.support_core_ktx)
    implementation(Dependencies.view_model_ktx)
    implementation(Dependencies.live_data_ktx)
    // Hilt
    implementation(Dependencies.hilt)
    kapt(Dependencies.hilt_compiler)
    implementation(Dependencies.hilt_view_model)
    kapt(Dependencies.hilt_androidx_compiler)

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
    implementation("androidx.fragment:fragment-ktx:1.2.5")
    implementation("com.github.ThuanPx:KtExt:1.1.4")
}
