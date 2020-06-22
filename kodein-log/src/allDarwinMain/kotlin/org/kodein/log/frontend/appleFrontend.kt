package org.kodein.log.frontend

import org.kodein.log.LogFrontend
import org.kodein.log.Logger
import org.kodein.log.darwin.*

public val iosFrontend: LogFrontend = { tag ->
    val log = darwin_log_create(tag.pkg, tag.name)

    ({ e, m ->
        val type = when (e.level) {
            Logger.Level.VERBOSE -> DARWIN_LOG_TYPE_DEBUG
            Logger.Level.INFO -> DARWIN_LOG_TYPE_INFO
            Logger.Level.WARNING -> DARWIN_LOG_TYPE_ERROR
            Logger.Level.ERROR -> DARWIN_LOG_TYPE_FAULT
        }

        darwin_log_with_type(log, type, m)
    })
}
