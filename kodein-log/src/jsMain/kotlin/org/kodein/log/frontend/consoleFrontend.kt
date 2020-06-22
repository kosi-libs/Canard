package org.kodein.log.frontend

import org.kodein.log.Logger
import org.kodein.log.LogFrontend


public val consoleFrontend: LogFrontend = { tag ->
    { entry, msg ->
        val logMsg =
                if (entry.meta.isEmpty()) msg
                else buildString {
                    append("$tag: $msg")
                    entry.meta.forEach { append("\n    ${it.key}: ${it.value}") }
                }

        val log = when (entry.level) {
            Logger.Level.VERBOSE -> console::log
            Logger.Level.INFO -> console::info
            Logger.Level.WARNING -> console::warn
            Logger.Level.ERROR -> console::error
        }

        if (entry.ex != null) log(arrayOf(logMsg, entry.ex))
        else log(arrayOf(logMsg))
    }
}
