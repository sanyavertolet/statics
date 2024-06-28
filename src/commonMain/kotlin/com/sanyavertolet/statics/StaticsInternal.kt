/**
 * File containing statics implementation
 */

package com.sanyavertolet.statics

import io.ktor.server.application.*
import io.ktor.server.routing.*
import okio.Path.Companion.DIRECTORY_SEPARATOR
import okio.Path.Companion.toPath

internal const val PATH_PARAMETER_NAME = "static-content-path-parameter"

/**
 * Sets up Routing to serve resources as static content.
 * All resources inside [basePackage] will be accessible.
 * If requested resource doesn't exist and index is not null, then response will be [index] resource in the requested package.
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
    route(remotePath) {
        route("{$PATH_PARAMETER_NAME...}") {
            get {
                val relativePath = call.parameters.getAll(PATH_PARAMETER_NAME)
                    ?.joinToString(DIRECTORY_SEPARATOR)
                    .orEmpty()
                val filePath = basePackage.orEmpty().toPath(true) / relativePath
                respondWithFile(filePath)
            }
        }
    }
    index?.let {
        get("/") {
            val indexPath = basePackage.orEmpty().toPath(true) / index
            respondWithFile(indexPath)
        }
    }
}
