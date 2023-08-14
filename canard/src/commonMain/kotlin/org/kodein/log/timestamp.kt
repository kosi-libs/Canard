package org.kodein.log

import kotlin.jvm.JvmInline

@JvmInline
public value class Timestamp(public val msecSinceEpoch: ULong)

public expect fun now(): Timestamp

public expect fun Timestamp.toLocalString(): String
