package org.kodein.log.print

internal actual fun Throwable._printStackTrace() = println("[Unknown Stack Trace]")
