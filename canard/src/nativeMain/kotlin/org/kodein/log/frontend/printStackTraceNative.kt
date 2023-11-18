package org.kodein.log.frontend

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.cstr
import platform.posix.fdopen
import platform.posix.fprintf
import kotlin.experimental.ExperimentalNativeApi


@OptIn(ExperimentalForeignApi::class)
private val stderr = fdopen(2, "w")

@OptIn(ExperimentalForeignApi::class)
internal actual fun errPrintln(msg: String) { fprintf(stderr, "%s\n", msg.cstr) }

@OptIn(ExperimentalNativeApi::class)
internal actual fun Throwable.getStackTraceStrings(): Array<String> = getStackTrace()
