package org.kodein.log

import org.kodein.log.Logger.Tag
import org.kodein.log.filter.entry.blockList
import org.kodein.log.filter.entry.allowList
import kotlin.test.Test
import kotlin.test.assertEquals

class AllowListTests {

    @Test
    fun test_00_AllowList() {
        val frontend = TestFrontend()
        val conditions = allowList(listOf(Tag(this::class)))
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
    fun test_01_BlockList() {
        val frontend = TestFrontend()
        val conditions = blockList(listOf(Tag(this::class)))
        // TODO This doesn't work for js
        //  org.kodein.log.AllowListTests => kotlin.js.AllowListTests
        //  val conditions = blockList(packages = listOf("org.kodein.log"))
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
