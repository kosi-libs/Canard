package org.kodein.log.frontend

import android.util.Log
import org.kodein.log.LogFrontend
import org.kodein.log.Logger

public val androidFrontend: LogFrontend = { tag ->
    ({ entry, message ->
        when (entry.level) {
            Logger.Level.ERROR -> {
                Log.e(tag.toString(), message, entry.ex)
            }
            Logger.Level.WARNING -> {
                Log.w(tag.toString(), message, entry.ex)
            }
            Logger.Level.INFO -> {
                Log.i(tag.toString(), message, entry.ex)
            }
            Logger.Level.VERBOSE -> {
                Log.v(tag.toString(), message, entry.ex)
            }
        }
    })
}
