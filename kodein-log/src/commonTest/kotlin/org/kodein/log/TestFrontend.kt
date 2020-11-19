package org.kodein.log

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class TestFrontend : LogFrontend {
    val entries: MutableList<Triple<Logger.Tag, Logger.Entry, String?>> = ArrayList()

    override fun getReceiverFor(tag: Logger.Tag): LogReceiver =
        LogReceiver { entry, message ->
            entries += Triple(tag, entry.copy(instant = Instant.DISTANT_PAST), message)
        }
}

fun testEntry(level: Logger.Level, ex: Throwable? = null, meta: Map<String, Any> = emptyMap()): Logger.Entry =
    Logger.Entry(level, ex, meta, Instant.DISTANT_PAST)
