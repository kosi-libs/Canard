package org.kodein.log.frontend

import org.kodein.log.Logger
import org.kodein.log.LogFrontend


public val consoleFrontend: LogFrontend = { from ->
    val fromName = from.js.name
    { e, msg ->
        val logMsg =
                if (e.meta.isEmpty()) msg
                else buildString {
                    append("$fromName: $msg")
                    e.meta.forEach { append("\n    ${it.key}: ${it.value}") }
                }

        val log = when (e.level) {
            Logger.Level.VERBOSE -> console::log
            Logger.Level.INFO -> console::info
            Logger.Level.WARNING -> console::warn
            Logger.Level.ERROR -> console::error
        }

        if (e.ex != null) log(arrayOf(logMsg, e.ex))
        else log(arrayOf(logMsg))
    }
}
