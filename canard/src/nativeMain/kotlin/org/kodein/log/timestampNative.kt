package org.kodein.log

import kotlinx.cinterop.*
import platform.posix.*


@OptIn(UnsafeNumber::class, ExperimentalForeignApi::class)
public actual fun now(): Timestamp {
    memScoped {
        val spec = alloc<timespec>()
        clock_gettime(CLOCK_REALTIME.convert(), spec.ptr)
        return Timestamp(spec.tv_sec.toULong() * 1_000u + spec.tv_nsec.toULong() / 1_000_000u)
    }
}

@OptIn(UnsafeNumber::class, ExperimentalForeignApi::class)
public actual fun Timestamp.toLocalString(): String {
    memScoped {
        val ms = msecSinceEpoch % 1_000u
        val sec = alloc<time_tVar>()
        sec.value = (msecSinceEpoch / 1_000u).convert()
        val info = localtime(sec.ptr)

        val chars = allocArray<ByteVar>(32)
        strftime(chars, 31.convert(), "%Y-%m-%dT%H:%M:%S", info)

        return chars.toKString() + "." + ms.toString().padStart(3, '0')
    }
}
