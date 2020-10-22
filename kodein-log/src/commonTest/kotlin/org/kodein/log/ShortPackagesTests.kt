package org.kodein.log

import kotlin.test.Test
import kotlin.test.assertEquals

class ShortPackagesTests {

    @Test
    fun test00_KeepLast() {
        val frontend = TestFrontend()
        val factory = LoggerFactory(frontend.withShortPackageKeepLast(2))
        factory.newLogger("org.kodein.tests.log.shortener", "Tests").info { "Hey!" }

        assertEquals(
            listOf<Triple<Logger.Tag, Logger.Entry, String?>>(
                Triple(Logger.Tag("o.k.t.log.shortener", "Tests"), Logger.Entry(Logger.Level.INFO), "Hey!"),
            ),
            frontend.entries
        )
    }

    @Test
    fun test01_ShortenFirst() {
        val frontend = TestFrontend()
        val factory = LoggerFactory(frontend.withShortPackageShortenFirst(2))
        factory.newLogger("org.kodein.tests.log.shortener", "Tests").info { "Hey!" }

        assertEquals(
            listOf<Triple<Logger.Tag, Logger.Entry, String?>>(
                Triple(Logger.Tag("o.k.tests.log.shortener", "Tests"), Logger.Entry(Logger.Level.INFO), "Hey!"),
            ),
            frontend.entries
        )
    }

}
