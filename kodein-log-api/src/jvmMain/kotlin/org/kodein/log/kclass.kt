package org.kodein.log

import kotlin.reflect.KClass

actual val KClass<*>.platformSimpleName get() = java.simpleName

actual val KClass<*>.platformQualifiedName get() = java.name
