/**
 * File containing expect statics function
 */

package com.sanyavertolet.statics

import io.ktor.server.routing.*

/**
 * Sets up Routing to serve resources as static content.
 * All resources inside [basePackage] will be accessible.
 * If requested resource doesn't exist and index is not null, then response will be [index] resource in the requested package.
 * If requested resource doesn't exist and no [index] specified, response will be `404 Not Found`.
 *
 * On JVM, staticResources is used by default.
 * If you want to use files from some directory instead of .jar file, use [com.sanyavertolet.statics.alternativeStatics] (not recommended)
 *
 * @param remotePath http path that defines requests that fetch statics
 * @param basePackage directory where all the statics are stored
 * @param index name of a fallback file
 */
expect fun Routing.statics(remotePath: String, basePackage: String?, index: String? = "index.html")
