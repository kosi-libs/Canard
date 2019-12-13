package org.kodein.log.print

import android.util.Log
import org.kodein.log.Logger
import org.kodein.log.LoggerFilter
import org.kodein.log.currentTimeStr
import org.kodein.log.platformQualifiedName

val androidLogFilter: LoggerFilter = {
    when (it.level) {
        Logger.Level.ERROR -> {
            Log.e(it.from.platformQualifiedName, it.msg, it.ex)
        }
        Logger.Level.WARNING -> {
            Log.w(it.from.platformQualifiedName, it.msg, it.ex)
        }
        Logger.Level.INFO -> {
            Log.i(it.from.platformQualifiedName, it.msg)
        }
        Logger.Level.VERBOSE -> {
            Log.v(it.from.platformQualifiedName, it.msg)
        }
    }
    it
}


