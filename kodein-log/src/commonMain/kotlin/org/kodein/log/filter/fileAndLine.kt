package org.kodein.log.filter

import org.kodein.log.LogFilter

data class SourceLine(val cls: String, val method: String, val file: String?, val line: Int) {
    override fun toString(): String = "$cls.$method $file:$line"
}

internal expect fun getCurrentSourceLine(): SourceLine?

val sourceLine: LogFilter = { _, e ->
    getCurrentSourceLine()
            ?.let { e.copy(meta = e.meta + ("fileAndLine" to it)) }
            ?: e
}
