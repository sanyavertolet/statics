package com.sanyavertolet.statics.buildutils

run {
    @Suppress("RUN_IN_SCRIPT")
    plugins {
        id("com.sanyavertolet.statics.buildutils.detekt-convention-configuration")
        id("com.sanyavertolet.statics.buildutils.diktat-convention-configuration")
    }
}
