package org.kodein.log

import java.text.SimpleDateFormat
import java.util.*

private val dtf =  SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")

public actual fun currentTimeStr(): String = dtf.format(Date())
