package org.kodein.log.android

import android.util.Log
import org.kodein.log.Logger
import org.kodein.log.LoggerReporter
import org.kodein.log.platformName

val androidLogReporter: LoggerReporter = {
    when (it.level) {
        Logger.Level.ERROR -> {
            Log.e(it.from.platformName, it.msg, it.ex)
        }
        Logger.Level.WARNING -> {
            Log.w(it.from.platformName, it.msg, it.ex)
        }
        Logger.Level.INFO -> {
            Log.i(it.from.platformName, it.msg)
        }
        Logger.Level.VERBOSE -> {
            Log.v(it.from.platformName, it.msg)
        }
    }
}
