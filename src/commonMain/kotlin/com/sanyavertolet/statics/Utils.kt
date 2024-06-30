/**
 * File that contains different utils
 */

package com.sanyavertolet.statics

import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.defaultForFileExtension
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.util.pipeline.PipelineContext
import okio.FileSystem
import okio.Path
import okio.SYSTEM

/**
 * Respond to request with content of file with [filePath] setting [ContentType] suitable for file according to its extension
 *
 * @param filePath path to file
 * @return Unit
 */
internal suspend fun PipelineContext<Unit, ApplicationCall>.respondWithFile(
    filePath: Path,
) = filePath.readFile()?.let { content ->
    val contentType = ContentType.defaultForFileExtension(filePath.name.substringAfterLast('.', ""))
    call.respondText(content, contentType)
} ?: call.respond(HttpStatusCode.NotFound, "File not found: $filePath")

/**
 * Read the content of [Path] as UTF-8
 *
 * @return file content as [String] if file is present, null otherwise
 */
internal fun Path.readFile(): String? {
    if (!FileSystem.SYSTEM.exists(this)) {
        return null
    }
    return buildString {
        FileSystem.SYSTEM.read(this@readFile) {
            while (!exhausted()) {
                readUtf8Line()?.let { append(it) }
            }
        }
    }
}
