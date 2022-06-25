package org.kodein.log.filter.message

import org.kodein.log.LogMapper


public fun replace(value: String, redacted: String): LogMapper = LogMapper { _, _, message ->
    message.replace(value, redacted)
}

public fun replace(regex: Regex, redacted: String): LogMapper = LogMapper { _, _, message ->
    message.replace(regex, redacted)
}
