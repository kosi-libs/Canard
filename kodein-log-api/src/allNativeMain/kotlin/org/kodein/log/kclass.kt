package org.kodein.log

import kotlin.reflect.KClass

actual val KClass<*>.platformSimpleName get() = simpleName ?: "?"

actual val KClass<*>.platformQualifiedName get() = simpleName ?: "?"
