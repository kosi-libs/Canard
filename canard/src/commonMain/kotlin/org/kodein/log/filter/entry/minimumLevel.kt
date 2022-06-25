package org.kodein.log.filter.entry

import org.kodein.log.LogFilter
import org.kodein.log.Logger

public fun minimumLevel(level: Logger.Level): LogFilter = LogFilter { _, entry ->
    if (entry.level.severity >= level.severity) entry
    else null
}
