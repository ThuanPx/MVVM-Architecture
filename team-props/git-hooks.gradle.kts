tasks.register("copyGitHooks", Copy::class) {
    description = "Copies the git hooks from team-props/git-hooks to the .git folder."
    val path = "$rootDir/team-props/git-hooks"
    from(path) {
        include("**/*.sh")
        rename("(.*).sh", "$1")
    }
    into("$rootDir/.git/hooks")
}

tasks.register("installGitHooks", Exec::class) {
    description = "Installs the pre-commit git hooks from team-props/git-hooks."
    group = "git hooks"
    workingDir = rootDir
    setCommandLine("chmod", "-R", "+x", ".git/hooks/")
    dependsOn("copyGitHooks")
    doLast {
        logger.info("Git hook installed successfully!")
    }
}

afterEvaluate {
    tasks["clean"].dependsOn("installGitHooks")
}
