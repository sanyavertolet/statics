package com.sanyavertolet.statics

import io.ktor.server.routing.*

expect fun Routing.statics(remotePath: String, basePackage: String?, index: String? = "index.html")
