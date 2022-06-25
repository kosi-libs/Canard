package org.kodein.log

import kotlin.reflect.KClass

public expect val KClass<*>.platformSimpleName: String

public expect val KClass<*>.platformPackageName: String
