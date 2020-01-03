package org.kodein.log

import org.kodein.log.filter.getCurrentSourceLine
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FileAndLineTests {

    @Test
    fun test00_FileAndLine() {
        val fl = getCurrentSourceLine()
        if (fl != null) {
            assertTrue(fl.cls.endsWith("FileAndLineTests"), "Expected \"${fl.cls}\" to end with \"FileAndLineTests\"")
            assertTrue(fl.method.startsWith("test00_FileAndLine"), "Expected \"${fl.method}\" to start with \"test00_FileAndLine\"")
            if (fl.file != null) assertEquals("FileAndLineTests.kt", fl.file)
            if (fl.line != -1) assertEquals(12, fl.line)
        }
    }

}
