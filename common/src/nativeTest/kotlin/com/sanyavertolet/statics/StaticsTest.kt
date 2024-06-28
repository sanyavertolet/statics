package com.sanyavertolet.statics

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import io.ktor.util.*
import io.ktor.utils.io.charsets.*
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.refTo
import kotlinx.cinterop.toKString
import okio.FileSystem
import okio.Path.Companion.toPath
import platform.posix.getcwd
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class StaticsTest {
    private val basePackage = "src".toPath(true)
        .div("nativeTest")
        .div("resources")
        .div("public")

    @Test
    fun indexTest() = testApplication {
        routing {
            statics("/", basePackage.toString())
        }

        assertTrue { FileSystem.SYSTEM.exists(basePackage / "index.html") }

        val response = client.get("/index.html")
        assertEquals(HttpStatusCode.OK, response.status)

        val indexPage: String = response.body()
        assertContains(indexPage, "Hello, Kotlin!")
        assertContains(response.headers.toMap(), HttpHeaders.ContentType)
        assertEquals(
            ContentType.Text.Html.withCharset(Charsets.UTF_8).toString(),
            response.headers[HttpHeaders.ContentType],
        )
    }

    @Test
    fun jsTest() = testApplication {
        routing {
            statics("/", basePackage.toString())
        }

        val response = client.get("/client.js")
        assertEquals(HttpStatusCode.OK, response.status)

        val jsScript: String = response.body()
        assertContains(jsScript, "Hello, Kotlin!")
        assertContains(response.headers.toMap(), HttpHeaders.ContentType)
        assertEquals(
            ContentType.Application.JavaScript.toString(),
            response.headers[HttpHeaders.ContentType],
        )
    }

    @Test
    fun cssTest() = testApplication {
        routing {
            statics("/", basePackage.toString())
        }

        val response = client.get("/styles.css")
        assertEquals(HttpStatusCode.OK, response.status)

        val cssFile: String = response.body()
        assertContains(cssFile, "font-family: Arial, sans-serif;")
        assertContains(response.headers.toMap(), HttpHeaders.ContentType)
        assertEquals(
            ContentType.Text.CSS.withCharset(Charsets.UTF_8).toString(),
            response.headers[HttpHeaders.ContentType],
        )
    }

    @Test
    fun notFoundTest() = testApplication {
        routing {
            statics("/", basePackage.toString())
        }

        val response = client.get("/i-dont-exist.js")
        assertEquals(HttpStatusCode.NotFound, response.status)
    }

    @Test
    fun `should not overlap existing routes`() = testApplication {
        routing {
            get("/hello") {
                call.respondText("Hello, ktor!")
            }
            statics("/", basePackage.toString())
        }

        val response = client.get("/hello")
        assertEquals(HttpStatusCode.OK, response.status)
        val content: String = response.body()
        assertContains(content, "Hello, ktor!")
    }
}
