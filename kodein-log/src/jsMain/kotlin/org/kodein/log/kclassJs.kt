package org.kodein.log

import kotlin.reflect.KClass

actual val KClass<*>.platformName get() = simpleName ?: "?"

actual val KClass<*>.packageName: String get() = error("JS does not support package name reflection")
