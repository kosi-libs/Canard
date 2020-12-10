package org.kodein.log

import org.kodein.log.Logger.Tag
import org.kodein.log.filter.message.prefix
import org.kodein.log.filter.message.replace
import kotlin.test.Test
import kotlin.test.assertEquals

class MappersTests {

    @Test
    fun test_00_Prefix() {
        val frontend = TestFrontend()
        val factory = LoggerFactory(listOf(frontend)).append(prefix("test - "))
        newLogger(factory).apply {
            info { "Hello," }
            warning { "World!" }
        }

        assertEquals(
            listOf<Triple<Tag, Logger.Entry, String?>>(
                Triple(Tag(this::class), frontend.testEntry(Logger.Level.INFO), "test - Hello,"),
                Triple(Tag(this::class), frontend.testEntry(Logger.Level.WARNING), "test - World!")
            ),
            frontend.entries
        )
    }

    @Test
    fun test_01_ReplaceString() {
        val frontend = TestFrontend()
        val factory = LoggerFactory(listOf(frontend), mappers = listOf(replace("0123456789abcedf", "[SECRET]")))
        newLogger(factory).apply {
            info { "User says hello!" }
            debug { "User created secret key 0123456789abcedf." }
        }

        assertEquals(
            listOf<Triple<Tag, Logger.Entry, String?>>(
                Triple(Tag(this::class), frontend.testEntry(Logger.Level.INFO), "User says hello!"),
                Triple(Tag(this::class), frontend.testEntry(Logger.Level.DEBUG), "User created secret key [SECRET].")
            ),
            frontend.entries
        )
    }

    @Test
    fun test_02_ReplaceRegex() {
        val frontend = TestFrontend()
        val factory = LoggerFactory(listOf(frontend), mappers = listOf(replace(Regex("0x[0-9a-fA-F]+"), "[SOME-HEX]")))
        newLogger(factory).apply {
            info { "User says hello!" }
            debug { "User created secret key 0x0123456789abcedf." }
        }

        assertEquals(
            listOf<Triple<Tag, Logger.Entry, String?>>(
                Triple(Tag(this::class), frontend.testEntry(Logger.Level.INFO), "User says hello!"),
                Triple(Tag(this::class), frontend.testEntry(Logger.Level.DEBUG), "User created secret key [SOME-HEX].")
            ),
            frontend.entries
        )
    }

}
