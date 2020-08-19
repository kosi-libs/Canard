package org.kodein.log.filter

import org.kodein.log.LogFilter
import org.kodein.log.frontend.getStackTraceStrings

public val logStackTrace: LogFilter = LogFilter { _, e ->
    e.copy(meta = e.meta + ("logStackTrace" to Throwable().getStackTraceStrings().joinToString("\n")))
}
