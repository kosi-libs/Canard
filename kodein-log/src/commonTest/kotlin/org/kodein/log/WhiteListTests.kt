package org.kodein.log

import org.kodein.log.Logger.Tag
import org.kodein.log.filter.Condition.IsTag
import org.kodein.log.filter.conditionList
import kotlin.test.Test
import kotlin.test.assertEquals

class WhiteListTests {

    private class TestFrontendBuilder {
        val entries: MutableList<Triple<Tag, Logger.Entry, String?>> = ArrayList()

        operator fun unaryPlus(): LogFrontend = { tag ->
            { entry, message ->
                entries += Triple(tag, entry, message)
            }
        }

    }

    @Test
    fun test00_WhiteList() {
        val builder = TestFrontendBuilder()
        val conditions = conditionList(false, listOf(
                IsTag(Tag(this::class), true)
        ))
        val factory = LoggerFactory(listOf(+builder), listOf(conditions))
        factory.newLogger(this::class).info { "THIS" }
        factory.newLogger<String>().warning { "STRING" }

        assertEquals<List<Triple<Tag, Logger.Entry, String?>>>(listOf(Triple(Tag(this::class), Logger.Entry(Logger.Level.INFO), "THIS")), builder.entries)
    }

    @Test
    fun test01_BlackList() {
        val builder = TestFrontendBuilder()
        val conditions = conditionList(true, listOf(
                IsTag(Tag(this::class), false)
        ))
        val factory = LoggerFactory(listOf(+builder), listOf(conditions))
        factory.newLogger(this::class).info { "THIS" }
        factory.newLogger<String>().warning { "STRING" }

        assertEquals<List<Triple<Tag, Logger.Entry, String?>>>(listOf(Triple(Tag(String::class), Logger.Entry(Logger.Level.WARNING), "STRING")), builder.entries)
    }

}
