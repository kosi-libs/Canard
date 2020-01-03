package org.kodein.log.filter


internal actual fun getCurrentSourceLine(): SourceLine? {
    val stack = (Throwable().asDynamic().stack as? String?)?.split("\n") ?: return null
    val infos = stack[3].trim().substring("at ".length)
    val (cm, fl) = infos.split(" ")
    val (c, m) = cm.split(".")
    val (f, l) = fl.substring(1, fl.length - 1).split("/").last().split(":")

    return (
            if (f.startsWith("adapter-browser.js")) SourceLine(c, m, null, -1)
            else SourceLine(c, m, f, l.toIntOrNull() ?: -1)
    )
}
