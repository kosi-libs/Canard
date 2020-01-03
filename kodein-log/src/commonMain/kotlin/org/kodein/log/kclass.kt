package org.kodein.log

import kotlin.reflect.KClass

expect val KClass<*>.platformName: String

expect val KClass<*>.packageName: String
