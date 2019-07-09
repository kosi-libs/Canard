package org.kodein.log

import kotlinx.cinterop.*
import platform.posix.*

@Suppress("EXPERIMENTAL_UNSIGNED_LITERALS")
actual fun currentTimeStr(): String {
    memScoped {
        val curTime = alloc<timeval>()
        gettimeofday(curTime.ptr, null)
        val ms = curTime.tv_usec / 1000
        val info = localtime(curTime.tv_sec.toLong().toCPointer())

        val chars = allocArray<ByteVar>(100)

        strftime(chars, 99.convert(), "%Y/%m/%d %H:%M:%S", info)

        return chars.toKString() + ":" + ms.toString().padStart(3, '0')
    }
}
