package org.kodein.log

import org.kodein.log.Logger.Tag
import org.kodein.log.filter.entry.Condition.IsTag
import org.kodein.log.filter.entry.conditionList
import kotlin.test.Test
import kotlin.test.assertEquals

class WhiteListTests {

    @Test
    fun test_00_WhiteList() {
        val frontend = TestFrontend()
        val conditions = conditionList(false, listOf(
                IsTag(Tag(this::class), true)
        ))
        val factory = LoggerFactory(listOf(frontend), listOf(conditions))
        newLogger(factory).info { "THIS" }
        factory.newLogger<String>().warning { "STRING" }

        assertEquals(
            listOf<Triple<Tag, Logger.Entry, String?>>(
                Triple(Tag(this::class), frontend.testEntry(Logger.Level.INFO), "THIS")
            ),
            frontend.entries
        )
    }

    @Test
    fun test_01_BlackList() {
        val frontend = TestFrontend()
        val conditions = conditionList(true, listOf(
                IsTag(Tag(this::class), false)
        ))
        val factory = LoggerFactory(listOf(frontend), listOf(conditions))
        newLogger(factory).info { "THIS" }
        factory.newLogger<String>().warning { "STRING" }

        assertEquals(
            listOf<Triple<Tag, Logger.Entry, String?>>(
                Triple(Tag(String::class), frontend.testEntry(Logger.Level.WARNING), "STRING")
            ),
            frontend.entries
        )
    }

}
