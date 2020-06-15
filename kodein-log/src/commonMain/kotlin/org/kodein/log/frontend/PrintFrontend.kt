package org.kodein.log.frontend

import org.kodein.log.Logger
import org.kodein.log.LogFrontend
import org.kodein.log.currentTimeStr
import org.kodein.log.platformName


public val printFrontend: LogFrontend = { f ->
    val platform = f.platformName

    { e, msg ->
        val log: (String) -> Unit = if (e.level == Logger.Level.ERROR) ({ println(it) }) else ({ errPrintln(it) })

        val prefix = " ".repeat(7 - e.level.name.length) + "${e.level.name}: ${currentTimeStr()} |"
        val indent = " ".repeat(prefix.length)
        if (msg != null) {
            msg.lines().forEachIndexed { i, l ->
                if (i == 0) log("$prefix $platform: $l")
                else log("$indent $l")
            }
        } else {
            log("$prefix | $platform")
        }

        e.meta.forEach { log("$indent     ${it.key}: ${it.value}") }

        e.ex?.logStackTrace("$indent   ", log)
    }
}

internal expect fun errPrintln(msg: String)

internal expect fun Throwable.getStackTraceStrings(): Array<String>

internal fun Throwable.logStackTrace(indent: String, log: (String) -> Unit) {
    log("$indent$this")

    getStackTraceStrings().forEach { log("$indent    at $it") }

    var cause = this.cause
    while (cause != null) {
        log("$indent  Caused by: $cause")
        cause.getStackTraceStrings().forEach { log("$indent      at $it") }
        cause = cause.cause
    }
}
