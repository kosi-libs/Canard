package org.kodein.log.frontend


internal actual fun errPrintln(msg: String) = System.err.println(msg)

internal actual fun Throwable.getStackTraceStrings(): Array<String> {
    return stackTrace.map { it.toString() } .toTypedArray()
}
