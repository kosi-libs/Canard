package org.kodein.log

import kotlinx.cinterop.*
import platform.posix.*

@Suppress("EXPERIMENTAL_UNSIGNED_LITERALS")
actual fun currentTimeStr(): String {
    memScoped {
        val curTime = alloc<timeval>()
        gettimeofday(curTime.ptr, null)
        val ms = curTime.tv_usec / 1000
        val sec = alloc<time_tVar>()
        sec.value = curTime.tv_sec
        val info = localtime(sec.ptr)

        val chars = allocArray<ByteVar>(100)

        strftime(chars, 99.convert(), "%Y-%m-%d %H:%M:%S", info)

        return chars.toKString() + ":" + ms.toString().padStart(3, '0')
    }
}
