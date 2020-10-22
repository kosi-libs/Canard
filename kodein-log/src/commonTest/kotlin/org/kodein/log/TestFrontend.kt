package org.kodein.log

class TestFrontend : LogFrontend {
    val entries: MutableList<Triple<Logger.Tag, Logger.Entry, String?>> = ArrayList()

    override fun getReceiverFor(tag: Logger.Tag): LogReceiver =
        LogReceiver { entry, message ->
            entries += Triple(tag, entry, message)
        }
}
