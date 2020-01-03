package org.kodein.log

import kotlin.reflect.KClass

actual val KClass<*>.platformName get() = qualifiedName ?: "?"

actual val KClass<*>.packageName: String get() {
    val qn = qualifiedName ?: error("Could not get qualified name of $this")
    val sn = qualifiedName ?: error("Could not get simple name of $this")
    return qn.substring(0, qn.length - sn.length - 1)
}
