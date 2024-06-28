@file:JvmName("StaticResourcesJvm")
package com.sanyavertolet.statics

import io.ktor.server.http.content.*
import io.ktor.server.routing.*

actual fun Routing.statics(
    remotePath: String,
    basePackage: String?,
    index: String?,
) {
    staticResources(remotePath, basePackage, index)
}

@Deprecated(
    "Use statics(remotePath, basePackage, index) instead",
    ReplaceWith("statics(remotePath, basePackage, index)"),
)
fun Routing.alternativeStatics(
    remotePath: String,
    basePackage: String?,
    index: String? = "index.html",
) = staticsInternal(remotePath, basePackage, index)
