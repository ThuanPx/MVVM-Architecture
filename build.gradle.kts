buildscript {
    // TODO fix later
    // apply(from = "$rootDir/team-props/git-hooks.gradle.kts")

    repositories {
        google()
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath(ClassPaths.android_gradle_plugin)
        classpath(ClassPaths.kotlin_gradle_plugin)
        classpath(ClassPaths.hilt_gradle_plugin)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { setUrl("https://jitpack.io") }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
