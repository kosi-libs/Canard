package org.kodein.log.filter

internal actual fun getCurrentSourceLine(): SourceLine? {
    val stack = Throwable().getStackTrace()

    println(stack[2])

    val comp = stack[2].split(" ")[0]
            .let { if (it.startsWith("kfun:")) it.substring("kfun:".length) else it }
            .split(".")

    val cls = comp.subList(0, comp.lastIndex).joinToString(".")
    val met = comp.last()

    return SourceLine(cls, met, null, -1)
}
