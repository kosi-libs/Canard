package org.kodein.log

import kotlin.reflect.KClass

public actual val KClass<*>.platformName: String get() = simpleName ?: "?"

public actual val KClass<*>.packageName: String get() = error("JS does not support package name reflection")
