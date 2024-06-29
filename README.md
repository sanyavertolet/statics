# Ktor Multiplatform Statics for Server

This project is a tiny library for `Ktor`-based apps designed to serve static files such as HTML, JavaScript, and CSS.
The problem is that Ktor has `staticResources()` on `jvm`, while `native` does not have any easy mechanism to configure serving static files.

`Ktor-statics` provides a function available on `jvm`, `linuxX64`, `linuxArm64`, `macosX64`, and `macosArm64` targets:
```kotlin
statics(remotePath = "/", basePackage = "path/to/resource/folder", index = "index.html")
```

* `remotePath` - prefix for `statics` requests;
* `basePackage` - path to `statics` (on `jvm` - path to resources inside your `.jar`, on `native` - path to folder);
* `index` - file that should be served if no path matches.

#### On `jvm`, `statics` uses `staticResources` under the hood. If you want same behaviour on `jvm`, use `alternativeStatics`.

## Installation and Usage
1. Add GitHub maven repository to your `settings.gradle.kts` file:
```kotlin
   dependencyResolutionManagement { 
       repositories { 
           mavenCentral()
           maven { 
               name = "sanyavertolet/statics"
               url = uri("https://maven.pkg.github.com/sanyavertolet/statics")
               credentials { 
                   username = providers.gradleProperty("gpr.user").orNull ?: System.getenv("GITHUB_ACTOR")
                   password = providers.gradleProperty("gpr.key").orNull ?: System.getenv("GITHUB_TOKEN") 
               } 
           } 
       }
   }
```
For more information, read [GitHub Docs](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry).

2. Add `com.sanyavertolet.statics:ktor-statics:{STATICS_VERSION}` to dependencies in your `build.gradle.kts` file:
```kotlin
    kotlin {
        jvm()
        linuxX64()
    
        sourceSets {
            commonMain {
                dependencies {
                    // other dependencies
                    implementation("com.sanyavertolet.statics:ktor-statics:${STATICS_VERSION}")
                }
            }
        }
    }
```

3. Use `statics` in your routing configuration:
```kotlin
// jvm
fun Application.module() {
    // some other configurations
    routing {
        // other routing configuration
        statics("/", "public")
    }
}

// native
fun Application.module() {
    // some other configurations
    routing {
        // other routing configuration
        statics("/", "/path/to/public")
    }
}
```

## Contributing
All the contributions are welcomed!
Please see [CONTRIBUTING.md](CONTRIBUTING.md) file for details on how to get started.

## Support
For support or to report bugs, please open an issue on [statics issues](https://github.com/sanyavertolet/statics/issues) page.

## License
`statics` is licensed under the `MIT Licence`.
See the [LICENSE](LICENSE) file for details.
