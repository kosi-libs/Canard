package org.kodein.log

import kotlin.reflect.KClass

public actual val KClass<*>.platformSimpleName: String get() = simpleName ?: "?"

public actual val KClass<*>.platformPackageName: String get() = "kotlin.wasm"
