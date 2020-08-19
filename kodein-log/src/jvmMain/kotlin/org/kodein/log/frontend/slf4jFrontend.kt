package org.kodein.log.frontend

import org.kodein.log.LogFrontend
import org.kodein.log.LogReceiver
import org.kodein.log.Logger
import org.slf4j.LoggerFactory

public val slf4jFrontend: LogFrontend = LogFrontend { tag ->
    val logger = LoggerFactory.getLogger(tag.toString())
    LogReceiver { entry, message ->
        val logMsg =
                if (entry.meta.isEmpty()) message
                else buildString {
                    append("$message")
                    entry.meta.forEach { append("\n    ${it.key}: ${it.value}") }
                }

        when (entry.level) {
            Logger.Level.VERBOSE -> logger.debug(logMsg, entry.ex)
            Logger.Level.INFO -> logger.info(logMsg, entry.ex)
            Logger.Level.WARNING -> logger.warn(logMsg, entry.ex)
            Logger.Level.ERROR -> logger.error(logMsg, entry.ex)
        }
    }
}
