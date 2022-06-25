package org.kodein.log.filter.message

import org.kodein.log.LogMapper


public fun prefix(prefix: String): LogMapper = LogMapper { _, _, message ->
    prefix + message
}
