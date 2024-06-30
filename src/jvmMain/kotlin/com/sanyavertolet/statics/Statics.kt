/**
 * File containing JVM implementation of statics
 */

@file:JvmName("StaticResourcesJvm")

package com.sanyavertolet.statics

import io.ktor.server.http.content.staticResources
import io.ktor.server.routing.Routing

/**
 * Behaves just as `staticResources(remotePath, basePackage, index)`
 *
 * If statics are located outside the `.jar`, use `alternativeStatics(remotePath, basePackage, index)`
 *
 * @see io.ktor.server.http.content.staticResources
 * @see alternativeStatics
 */
actual fun Routing.statics(
    remotePath: String,
    basePackage: String?,
    index: String?,
) {
    staticResources(remotePath, basePackage, index)
}

/**
 * Sets up Routing to serve resources as static content.
 * All resources inside [basePackage] will be accessible.
 * If requested resource doesn't exist and [index] is not null, then response will be [index] resource in the requested package.
 * If requested resource doesn't exist and no [index] specified, response will be `404 Not Found`.
 *
 * @param remotePath http path that defines requests that fetch statics
 * @param basePackage directory where all the statics are stored
 * @param index name of a fallback file
 * @return [Unit]
 */
@Deprecated(
    "Use statics(remotePath, basePackage, index) instead",
    ReplaceWith("statics(remotePath, basePackage, index)"),
)
fun Routing.alternativeStatics(
    remotePath: String,
    basePackage: String?,
    index: String? = "index.html",
) = staticsInternal(remotePath, basePackage, index)
