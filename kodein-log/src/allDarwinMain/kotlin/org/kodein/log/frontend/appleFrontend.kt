package org.kodein.log.frontend

import kotlinx.cinterop.ptr
import org.kodein.log.LogFrontend
import org.kodein.log.Logger
import org.kodein.log.darwin.ios_log
import platform.darwin.*

public val iosFrontend: LogFrontend = { cls ->
    val names = cls.qualifiedName?.split(".") ?: cls.simpleName?.let { listOf(it) } ?: listOf("")
    val log =
            if (names.size >= 2) os_log_create(names.subList(0, names.size - 1).joinToString("."), names.last())
            else os_log_create(names.first(), "")

    ({ e, m ->
        val type = when (e.level) {
            Logger.Level.VERBOSE -> OS_LOG_TYPE_DEBUG
            Logger.Level.INFO -> OS_LOG_TYPE_INFO
            Logger.Level.WARNING -> OS_LOG_TYPE_ERROR
            Logger.Level.ERROR -> OS_LOG_TYPE_FAULT
        }

        ios_log(log, type, m)
    })
}
