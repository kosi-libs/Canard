package org.kodein.log

import org.kodein.log.filter.Condition.IsClass
import org.kodein.log.filter.conditionList
import kotlin.reflect.KClass
import kotlin.test.Test
import kotlin.test.assertEquals

class WhiteListTests {

    private class TestFrontend {
        val entries: MutableList<Triple<KClass<*>, Logger.Entry, String?>> = ArrayList()

        operator fun unaryPlus(): LogFrontend = { f ->
            { e, m ->
                entries += Triple(f, e, m)
            }
        }

    }

    @Test
    fun test00_WhiteList() {
        val r = TestFrontend()
        val l = conditionList(false, listOf(
                IsClass(this::class, true)
        ))
        val f = LoggerFactory(listOf(+r), listOf(l))
        f.newLogger(this::class).info { "THIS" }
        f.newLogger<String>().warning { "STRING" }

        assertEquals<List<Triple<KClass<*>, Logger.Entry, String?>>>(listOf(Triple(this::class, Logger.Entry(Logger.Level.INFO), "THIS")), r.entries)
    }

    @Test
    fun test01_BlackList() {
        val r = TestFrontend()
        val l = conditionList(true, listOf(
                IsClass(this::class, false)
        ))
        val f = LoggerFactory(listOf(+r), listOf(l))
        f.newLogger(this::class).info { "THIS" }
        f.newLogger<String>().warning { "STRING" }

        assertEquals<List<Triple<KClass<*>, Logger.Entry, String?>>>(listOf(Triple(String::class, Logger.Entry(Logger.Level.WARNING), "STRING")), r.entries)
    }

}
