package org.kodein.log.frontend

import org.kodein.log.Logger
import org.kodein.log.LogFrontend
import org.kodein.log.LogReceiver
import org.kodein.log.currentTimeStr


public fun stringFrontend(getPrinter: (Logger.Entry) -> (String) -> Unit): LogFrontend = LogFrontend { tag ->
    LogReceiver { entry, msg ->
        val printer = getPrinter(entry)

        val prefix = " ".repeat(7 - entry.level.name.length) + "${entry.level.name}: ${currentTimeStr()} |"
        val indent = " ".repeat(prefix.length)
        if (msg != null) {
            msg.lines().forEachIndexed { i, l ->
                if (i == 0) printer("$prefix $tag: $l")
                else printer("$indent $l")
            }
        } else {
            printer("$prefix $tag")
        }

        entry.meta.forEach { printer("$indent     ${it.key}: ${it.value}") }

        entry.ex?.logStackTrace("$indent   ", printer)
    }
}

public val printFrontend: LogFrontend = stringFrontend {
    if (it.level.severity >= Logger.Level.WARNING.severity) ::errPrintln else ::println
}

public val simplePrintFrontend: LogFrontend = stringFrontend { ::println }

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
