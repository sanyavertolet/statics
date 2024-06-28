plugins {
    alias(libs.plugins.ktor.plugin)
    alias(libs.plugins.kotlin.multiplatform)
}

group = "com.sanyavertolet.statics"
version = "0.0.1"

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
