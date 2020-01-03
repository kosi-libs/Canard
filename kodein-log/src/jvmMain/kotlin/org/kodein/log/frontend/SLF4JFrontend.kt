package org.kodein.log.frontend

import org.kodein.log.LogFrontend
import org.kodein.log.Logger
import org.slf4j.LoggerFactory

private val SLF4JMapperFrontend: LogFrontend = { from ->
    val logger = LoggerFactory.getLogger(from.java)
    ({ e, msg ->

        val logMsg =
                if (e.meta.isEmpty()) msg
                else buildString {
                    append("$msg")
                    e.meta.forEach { append("\n    ${it.key}: ${it.value}") }
                }

        when (e.level) {
            Logger.Level.VERBOSE -> logger.debug(logMsg, e.ex)
            Logger.Level.INFO -> logger.info(logMsg, e.ex)
            Logger.Level.WARNING -> logger.warn(logMsg, e.ex)
            Logger.Level.ERROR -> logger.error(logMsg, e.ex)
        }
    })
}

val SLF4JFrontend by lazy {
    try {
        Class.forName("org.slf4j.impl.StaticLoggerBinder")
        SLF4JMapperFrontend
    } catch (ex: ClassNotFoundException) {
        printFrontend(LoggerFactory::class)(Logger.Entry(Logger.Level.WARNING), "Failed to load class \"org.slf4j.impl.StaticLoggerBinder\".\nDefaulting to Kodein-Log print logger.\nSee http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.")
        printFrontend
    }
}
