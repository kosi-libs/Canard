package org.kodein.log

import kotlin.reflect.KClass

public actual val KClass<*>.platformName: String get() = java.name

public actual val KClass<*>.packageName: String get() = java.`package`.name
