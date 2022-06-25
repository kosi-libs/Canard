package org.kodein.log.frontend

import org.kodein.log.LogFrontend
import java.util.ServiceLoader

private fun isSlf4jAvailable(): Boolean {
    // SLF4J 1.0 - 1.7
    try {
        Class.forName("org.slf4j.impl.StaticLoggerBinder")
        return true
    } catch (_: ClassNotFoundException) {
    }

    // SLF4J 2.0+
    try {
        val slf4jSpi = Class.forName("org.slf4j.spi.SLF4JServiceProvider")
        val loader = ServiceLoader.load(slf4jSpi)
        return loader.any()
    } catch (_: ClassNotFoundException) {
    }

    return false
}

private fun isAndroidAvailable(): Boolean =
        try {
            Class.forName("android.util.Log")
            true
        } catch (_: ClassNotFoundException) {
            false
        }

public actual val defaultLogFrontend: LogFrontend by lazy {
    when {
        isSlf4jAvailable() -> slf4jFrontend
        isAndroidAvailable() -> androidFrontend
        else -> printFrontend
    }
}
