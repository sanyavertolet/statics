import com.sanyavertolet.statics.buildutils.configureSigning

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.dokka)

    id("com.sanyavertolet.statics.buildutils.code-quality-convention")
    id("com.sanyavertolet.statics.buildutils.versioning-configuration")
    id("com.sanyavertolet.statics.buildutils.publishing-configuration")
}

group = "com.sanyavertolet.statics"

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

        jvmMain {
            dependencies {
                implementation(libs.logback.classic)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.ktor.server.tests)
                implementation(kotlin("test"))
            }
        }
    }
}

tasks.withType<AbstractPublishToMaven> {
    dependsOn(tasks.withType<Sign>())
}

configureSigning()
