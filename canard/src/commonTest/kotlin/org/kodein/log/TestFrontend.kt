package org.kodein.log


class TestFrontend : LogFrontend {
    private val timestamp = now()

    val entries: MutableList<Triple<Logger.Tag, Logger.Entry, String?>> = ArrayList()

    override fun getReceiverFor(tag: Logger.Tag): LogReceiver =
        LogReceiver { entry, message ->
            entries += Triple(tag, entry.copy(timestamp = timestamp), message)
        }

    fun testEntry(level: Logger.Level, ex: Throwable? = null, meta: Map<String, Any> = emptyMap()): Logger.Entry =
        Logger.Entry(level, ex, meta, timestamp)
}
