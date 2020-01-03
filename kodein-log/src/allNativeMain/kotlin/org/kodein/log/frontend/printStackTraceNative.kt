package org.kodein.log.frontend

import kotlinx.cinterop.cstr
import platform.posix.*
import kotlin.native.concurrent.SharedImmutable


@SharedImmutable
private val stderr = fdopen(2, "w")

internal actual fun errPrintln(msg: String) { fprintf(stderr, "%s\n", msg.cstr) }

internal actual fun Throwable.getStackTraceStrings() = getStackTrace()
