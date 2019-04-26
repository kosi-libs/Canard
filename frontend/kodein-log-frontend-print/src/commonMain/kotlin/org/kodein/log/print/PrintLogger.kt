package org.kodein.log.print

import org.kodein.log.LoggerFilter
import org.kodein.log.currentTimeStr
import org.kodein.log.platformQualifiedName

val printLogFilter: LoggerFilter = {
    println("${it.level.name.substring(0..4)}: ${it.from.platformQualifiedName} - ${currentTimeStr()} / ${it.meta} / ${it.msg}")
    it.ex?._printStackTrace()
    it
}

internal expect fun Throwable._printStackTrace()
