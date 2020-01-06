package org.kodein.log.filter

import org.kodein.log.LogFilter
import org.kodein.log.frontend.getStackTraceStrings

val logStackTrace: LogFilter = { _, e ->
    e.copy(meta = e.meta + ("logStackTrace" to Throwable().getStackTraceStrings().joinToString("\n")))
}
