package org.kodein.log

import kotlinx.cinterop.*
import platform.posix.*


public actual class Instant(public val tv_sec: time_t, public val tv_nsec: Long)

public actual fun now(): Instant {
    memScoped {
        val spec = alloc<timespec>()
        clock_gettime(CLOCK_REALTIME, spec.ptr)
        return Instant(spec.tv_sec, spec.tv_nsec.convert())
    }
}

public actual fun Instant.toLocalString(): String {
    memScoped {
        val ms = tv_nsec / 1_000_000L
        val sec = alloc<time_tVar>()
        sec.value = tv_sec
        val info = localtime(sec.ptr)

        val chars = allocArray<ByteVar>(32)
        strftime(chars, 31.convert(), "%Y-%m-%dT%H:%M:%S", info)

        return chars.toKString() + "." + ms.toString().padStart(3, '0')
    }
}
