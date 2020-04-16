package org.kodein.log

import kotlinx.cinterop.*
import platform.posix.*

@Suppress("EXPERIMENTAL_UNSIGNED_LITERALS")
actual fun currentTimeStr(): String {
    memScoped {
        val ts = alloc<timespec>()
        clock_gettime(CLOCK_REALTIME, ts.ptr)

        @Suppress("RemoveRedundantCallsOfConversionMethods")
        val ms = ts.tv_nsec.toLong() / 1_000_000L
        val sec = alloc<time_tVar>()
        sec.value = ts.tv_sec
        val info = localtime(sec.ptr)

        val chars = allocArray<ByteVar>(32)
        strftime(chars, 31.convert(), "%Y-%m-%d %H:%M:%S", info)

        return chars.toKString() + ":" + ms.toString().padStart(3, '0')
    }
}
