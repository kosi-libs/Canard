package org.kodein.log

import org.kodein.log.Logger.Tag
import org.kodein.log.filter.Condition.IsTag
import org.kodein.log.filter.conditionList
import kotlin.test.Test
import kotlin.test.assertEquals

class PrefixTests {

    @Test
    fun test00_Prefix() {
        val frontend = TestFrontend()
        val factory = LoggerFactory(listOf(frontend))
        newLogger(factory).prefixed("#1").info { "THIS" }
        factory.newLogger<String>().prefixed("#2").warning { "STRING" }

        assertEquals(
            listOf<Triple<Tag, Logger.Entry, String?>>(
                Triple(Tag(this::class), testEntry(Logger.Level.INFO), "#1 - THIS"),
                Triple(Tag(String::class), testEntry(Logger.Level.WARNING), "#2 - STRING")
            ),
            frontend.entries
        )
    }
}
