package org.kodein.log

import java.text.SimpleDateFormat
import java.util.*


public actual fun now(): Timestamp = Timestamp(System.currentTimeMillis().toULong())

private val dtf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS")

public actual fun Timestamp.toLocalString(): String = dtf.format(Date(msecSinceEpoch.toLong()))
