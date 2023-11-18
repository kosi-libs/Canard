package org.kodein.log.frontend


internal actual fun errPrintln(msg: String) { println(msg) }

internal actual fun Throwable.getStackTraceStrings(): Array<String> =
    arrayOf("Stack traces are currently not supported in WASM")
