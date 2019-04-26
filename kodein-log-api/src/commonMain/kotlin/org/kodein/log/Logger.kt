package org.kodein.log

import kotlin.reflect.KClass

typealias LoggerFactory = (KClass<*>) -> Logger

typealias LoggerFilter = (Logger.Entry) -> Logger.Entry?

class Logger(val from: KClass<*>, vararg val filters: LoggerFilter) {

    enum class Level { VERBOSE, INFO, WARNING, ERROR }

    data class Entry(val level: Level, val from: KClass<*>, val msg: String?, val ex: Throwable?, val meta: Map<String, Any>)

    private fun log(level: Level, msg: String?, ex: Throwable?) {
        var e = Entry(level, from, msg, ex, emptyMap())
        for (filter in filters) {
            e = filter(e) ?: return
        }
    }

    fun verbose(msg: String) = log(Level.VERBOSE, msg, null)

    fun info(msg: String) = log(Level.INFO, msg, null)

    fun warning(msg: String, ex: Throwable? = null) = log(Level.WARNING, msg, ex)
    fun warning(ex: Throwable) = log(Level.WARNING, null, ex)

    fun error(msg: String, ex: Throwable? = null) = log(Level.ERROR, msg, ex)
    fun error(ex: Throwable) = log(Level.ERROR, null, ex)

    companion object
}

inline fun <reified T: Any> Logger.Companion.from(vararg filters: LoggerFilter): Logger = Logger(T::class, *filters)

fun <T: Any> Logger.Companion.from(receiver: T, vararg filters: LoggerFilter): Logger = Logger(receiver::class, *filters)

expect fun currentTimeStr(): String

expect val KClass<*>.platformSimpleName: String

expect val KClass<*>.platformQualifiedName: String
