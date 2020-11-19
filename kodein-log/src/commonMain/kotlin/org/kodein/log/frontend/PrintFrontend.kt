package org.kodein.log.frontend

import org.kodein.log.Logger
import org.kodein.log.LogFrontend
import org.kodein.log.LogReceiver
import org.kodein.log.toLocalString


public fun printLogIn(tag: Logger.Tag, entry: Logger.Entry, msg: String?, printer: (String) -> Unit) {
    val prefix = " ".repeat(7 - entry.level.name.length) + "${entry.level.name}: ${entry.instant.toLocalString()} |"
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

public fun printerFrontend(getPrinter: (Logger.Entry) -> (String) -> Unit): LogFrontend = LogFrontend { tag ->
    LogReceiver { entry, msg ->
        printLogIn(tag, entry, msg, getPrinter(entry))
    }
}

public val printFrontend: LogFrontend = printerFrontend {
    if (it.level.severity >= Logger.Level.WARNING.severity) ::errPrintln else ::println
}

public val simplePrintFrontend: LogFrontend = printerFrontend { ::println }

internal expect fun errPrintln(msg: String)

internal expect fun Throwable.getStackTraceStrings(): Array<String>

internal fun Throwable.logStackTrace(indent: String, printer: (String) -> Unit) {
    printer("$indent$this")

    getStackTraceStrings().forEach { printer("$indent    at $it") }

    var cause = this.cause
    while (cause != null) {
        printer("$indent  Caused by: $cause")
        cause.getStackTraceStrings().forEach { printer("$indent      at $it") }
        cause = cause.cause
    }
}
