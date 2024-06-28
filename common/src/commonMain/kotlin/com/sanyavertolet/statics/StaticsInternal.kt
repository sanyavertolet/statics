package com.sanyavertolet.statics

import io.ktor.server.application.*
import io.ktor.server.routing.*
import okio.Path.Companion.DIRECTORY_SEPARATOR
import okio.Path.Companion.toPath

internal const val PATH_PARAMETER_NAME = "static-content-path-parameter"

internal fun Route.staticsInternal(
    remotePath: String,
    basePackage: String?,
    index: String?,
) {
    route(remotePath) {
        route("{$PATH_PARAMETER_NAME...}") {
            get {
                val relativePath = call.parameters.getAll(PATH_PARAMETER_NAME)?.joinToString(DIRECTORY_SEPARATOR).orEmpty()
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
