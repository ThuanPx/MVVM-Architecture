plugins {
    id(Plugins.kotlin)
}

repositories {
    mavenCentral()
}


dependencies {
    implementation(Dependencies.kotlin_stdlib)
    compileOnly("com.pinterest.ktlint:ktlint-core:0.36.0")
}
