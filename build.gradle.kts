import com.sanyavertolet.statics.buildutils.configureSigning

group = "com.sanyavertolet.statics"

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.dokka)

    id("com.sanyavertolet.statics.buildutils.code-quality-convention")
    id("com.sanyavertolet.statics.buildutils.versioning-configuration")
    id("com.sanyavertolet.statics.buildutils.publishing-configuration")
}

repositories {
    mavenCentral()
}

kotlin {
    jvm()
    linuxX64()
    linuxArm64()
    macosX64()
    macosArm64()

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.ktor.server.core)
                implementation(libs.okio)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.ktor.server.tests)
                implementation(kotlin("test"))
            }
        }

        jvmTest {
            dependencies {
                implementation(libs.logback.classic)
            }
        }
    }
}

tasks.withType<AbstractPublishToMaven> {
    dependsOn(tasks.withType<Sign>())
}

configureSigning()

tasks.dokkaHtml {
    dokkaSourceSets {
        configureEach {
            includes.from("README.md")
        }
    }
}
