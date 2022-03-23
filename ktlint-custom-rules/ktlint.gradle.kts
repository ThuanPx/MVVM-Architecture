repositories {
    jcenter()
}
/**
 * Add gradle tasks incremental for ktlint check
 * Ref: https://medium.com/@vanniktech/making-your-gradle-tasks-incremental-7f26e4ef09c3
 */
val outputDir = "$buildDir/reports/ktlint/"
val inputFiles = "src/**/*.kt"
val outputFile = "${outputDir}ktlint.html"

val ktlint by configurations.creating
val ktlintCheck by tasks.creating(JavaExec::class) {
    group = "ktlint"
    description = "Check Kotlin code style."
    classpath = configurations.getByName("ktlint")
    inputs.files(inputFiles)
    outputs.dir(outputDir)
    main = "com.pinterest.ktlint.Main"
}

val ktlintFormat by tasks.creating(JavaExec::class) {
    group = "ktlint"
    description = "Fix Kotlin code style deviations."
    classpath = configurations.getByName("ktlint")
    main = "com.pinterest.ktlint.Main"
    args = listOf("-F", "$inputFiles")
}

dependencies {
    ktlint("com.pinterest:ktlint:0.36.0")
    ktlint(project(":ktlint-custom-rules"))
}