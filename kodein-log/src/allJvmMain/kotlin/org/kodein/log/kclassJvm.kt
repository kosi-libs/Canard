package org.kodein.log

import kotlin.reflect.KClass

actual val KClass<*>.platformName: String get() = java.name

actual val KClass<*>.packageName: String get() = java.`package`.name
