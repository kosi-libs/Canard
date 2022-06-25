package org.kodein.log

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


public actual typealias Instant = kotlinx.datetime.Instant

public actual fun now(): Instant = kotlinx.datetime.Clock.System.now()

public actual fun Instant.toLocalString(): String = toLocalDateTime(TimeZone.currentSystemDefault()).toString()
