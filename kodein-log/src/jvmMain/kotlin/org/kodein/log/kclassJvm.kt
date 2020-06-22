package org.kodein.log

import kotlin.reflect.KClass

public actual val KClass<*>.platformSimpleName: String get() = java.simpleName

public actual val KClass<*>.platformPackageName: String get() = java.`package`.name
