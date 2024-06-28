plugins {
    kotlin("multiplatform")
    id("io.ktor.plugin")
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
                implementation("io.ktor:ktor-server-core:2.3.11")
                implementation("io.ktor:ktor-server-cio:2.3.11")

                implementation("com.squareup.okio:okio:3.9.0")
            }
        }

        nativeMain {

        }

        jvmMain {
            dependencies {
                implementation("ch.qos.logback:logback-classic:1.5.6")
            }
        }

        commonTest {
            dependencies {
                implementation("io.ktor:ktor-server-tests:2.3.11")
                implementation(kotlin("test"))
            }
        }
    }
}

application {
    mainClass.set("com.sanyavertolet.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}
