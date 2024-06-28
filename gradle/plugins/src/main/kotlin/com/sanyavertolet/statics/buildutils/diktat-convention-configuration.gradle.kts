package com.sanyavertolet.statics.buildutils

plugins {
    id("com.saveourtool.diktat")
}

diktat {
    diktatConfigFile = rootProject.file("diktat-analysis.yml")
    githubActions = findProperty("diktat.githubActions")?.toString()?.toBoolean() ?: false
    inputs {
        // using `Project#path` here, because it must be unique in gradle's project hierarchy
        if (path == rootProject.path) {
            include("gradle/plugins/src/**/*.kt", "*.kts", "gradle/plugins/**/*.kts", "src/**/*.kt")
            exclude("gradle/plugins/build/**", "src/test/**/*.kt", "src/*Test/**/*.kt")
        } else {
            include("src/**/*.kt", "**/*.kts")
            exclude("src/test/**/*.kt", "src/*Test/**/*.kt")
        }
    }
}
