plugins {
    id("kotlin")
}

repositories {
    mavenCentral()
}


dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.6.10")
    compileOnly("com.pinterest.ktlint:ktlint-core:0.36.0")
}
