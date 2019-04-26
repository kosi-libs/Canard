package org.kodein.log

import kotlin.js.Date

actual fun currentTimeStr(): String = Date(Date.now()).let {
    val Y = it.getFullYear().toString()
    val M = it.getMonth().inc().toString().padStart(2, '0')
    val D = it.getDate().toString().padStart(2, '0')
    val h = it.getHours().toString().padStart(2, '0')
    val m = it.getMinutes().toString().padStart(2, '0')
    val s = it.getSeconds().toString().padStart(2, '0')
    val ss = it.getMilliseconds().toString().padStart(3, '0')

    "$Y/$M/$D $h:$m:$s:$ss"
}
