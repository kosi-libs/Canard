package org.kodein.log

import kotlin.reflect.KClass

public expect val KClass<*>.platformName: String

public expect val KClass<*>.packageName: String
