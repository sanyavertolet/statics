/**
 * File containing statics implementation
 */

package com.sanyavertolet.statics

import io.ktor.server.application.call
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.util.logging.KtorSimpleLogger
import okio.FileSystem
import okio.Path.Companion.DIRECTORY_SEPARATOR
import okio.Path.Companion.toPath
import okio.SYSTEM

internal const val PATH_PARAMETER_NAME = "static-content-path-parameter"

/**
 * Sets up Routing to serve resources as static content.
 * All resources inside [basePackage] will be accessible.
 * If requested resource doesn't exist and [index] is not null, then response will be [index] resource in the requested package.
 * If requested resource doesn't exist and no [index] specified, response will be `404 Not Found`.
 *
 * @param remotePath http path that defines requests that fetch statics
 * @param basePackage directory where all the statics are stored
 * @param index name of a fallback file
 */
internal fun Route.staticsInternal(
    remotePath: String,
    basePackage: String?,
    index: String?,
) {
    val basePackagePath = basePackage.orEmpty().toPath(true)
    if (!FileSystem.SYSTEM.exists(basePackagePath)) {
        KtorSimpleLogger("statics").warn("$basePackagePath doesn't exist.")
    }
    route(remotePath) {
        route("{$PATH_PARAMETER_NAME...}") {
            get {
                val relativePath = call.parameters.getAll(PATH_PARAMETER_NAME)
                    ?.joinToString(DIRECTORY_SEPARATOR)
                    .orEmpty()
                val filePath = basePackagePath / relativePath
                respondWithFile(filePath)
            }
        }
    }
    index?.let {
        get("/") {
            val indexPath = basePackagePath / index
            respondWithFile(indexPath)
        }
    }
}
