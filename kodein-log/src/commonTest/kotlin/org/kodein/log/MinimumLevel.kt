package org.kodein.log

import org.kodein.log.Logger.Tag
import org.kodein.log.filter.Condition.IsTag
import org.kodein.log.filter.conditionList
import org.kodein.log.filter.minimumLevel
import kotlin.test.Test
import kotlin.test.assertEquals

class MinimumLevel {

    @Test
    fun test00_MinimumLevel() {
        val frontend = TestFrontend()
        val filter = minimumLevel(Logger.Level.WARNING)
        val factory = LoggerFactory(listOf(frontend), listOf(filter))
        newLogger(factory).debug { "debug" }
        newLogger(factory).info { "info" }
        newLogger(factory).warning { "warning" }
        newLogger(factory).error { "error" }

        assertEquals(
            listOf<Triple<Tag, Logger.Entry, String?>>(
                Triple(Tag(this::class), testEntry(Logger.Level.WARNING), "warning"),
                Triple(Tag(this::class), testEntry(Logger.Level.ERROR), "error")
            ),
            frontend.entries
        )
    }

}
