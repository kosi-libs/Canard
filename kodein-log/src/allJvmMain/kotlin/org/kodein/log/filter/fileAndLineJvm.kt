package org.kodein.log.filter

internal actual fun getCurrentSourceLine(): SourceLine? = Throwable().stackTrace[1].let {
    SourceLine(it.className, it.methodName, it.fileName ?: "???", it.lineNumber)
}
