/**
 * File containing native implementation of statics
 */

package com.sanyavertolet.statics

import io.ktor.server.routing.*

actual fun Routing.statics(
    remotePath: String,
    basePackage: String?,
    index: String?
) = staticsInternal(remotePath, basePackage, index)
