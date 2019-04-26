package org.kodein.log

val fileAndLineFilter: LoggerFilter = {
    it.copy(
            meta = it.meta + ("location" to "somewhere.kt:123")
    )
}
