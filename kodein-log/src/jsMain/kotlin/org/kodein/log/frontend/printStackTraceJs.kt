package org.kodein.log.frontend

internal actual fun errPrintln(msg: String) = console.error(msg)

internal actual fun Throwable.getStackTraceStrings(): Array<String> {
    val stack = (asDynamic().stack as? String?)?.split("\n")
            ?.let {
                val name = asDynamic().name as? String? ?: this::class.js.name
                if (it[0].trim().startsWith(name)) it.drop(1) else it
            }
            ?.map { it.trim() }
            ?.map { if (it.startsWith("at ")) it.substring(3) else it }

    return stack?.toTypedArray() ?: arrayOf("[no stack trace]")
}
