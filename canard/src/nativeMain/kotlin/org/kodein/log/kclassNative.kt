package org.kodein.log

import kotlin.reflect.KClass

public actual val KClass<*>.platformSimpleName: String get() = simpleName ?: "?"

public actual val KClass<*>.platformPackageName: String get() {
    val qn = qualifiedName ?: error("Could not get qualified name of $this")
    val sn = simpleName ?: error("Could not get simple name of $this")
    return qn.substring(0, qn.length - sn.length - 1)
}
