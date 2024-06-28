package com.sanyavertolet.statics

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*
import okio.FileSystem
import okio.Path
import okio.SYSTEM

internal suspend fun PipelineContext<Unit, ApplicationCall>.respondWithFile(
    indexPath: Path,
) = indexPath.readFile()?.let { content ->
    val contentType = ContentType.defaultForFileExtension(indexPath.name.substringAfterLast('.', ""))
    call.respondText(content, contentType)
} ?: call.respond(HttpStatusCode.NotFound, "File not found: $indexPath")

internal fun Path.readFile(): String? {
    if(!FileSystem.SYSTEM.exists(this)) {
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
