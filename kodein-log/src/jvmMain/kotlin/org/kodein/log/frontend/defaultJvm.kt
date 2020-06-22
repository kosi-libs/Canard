package org.kodein.log.frontend

import org.kodein.log.LogFrontend

private fun isSlf4jAvailable(): Boolean =
        try {
            Class.forName("org.slf4j.impl.StaticLoggerBinder")
            true
        } catch (_: ClassNotFoundException) {
            false
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
