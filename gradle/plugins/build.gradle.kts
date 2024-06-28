plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

run {
    dependencies {
        implementation(libs.reckon.gradle.plugin)
        implementation(libs.detekt.gradle.plugin) {
            exclude("io.github.detekt.sarif4k", "sarif4k")
        }
        implementation(libs.diktat.gradle.plugin) {
            exclude("io.github.detekt.sarif4k", "sarif4k")
        }
        implementation(libs.sarif4k)
        implementation(libs.publish.gradle.plugin)
    }
}
