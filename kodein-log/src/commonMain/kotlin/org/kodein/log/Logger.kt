package org.kodein.log

import org.kodein.log.frontend.defaultLogFrontend
import kotlin.reflect.KClass


typealias LogFilter = (KClass<*>, Logger.Entry) -> Logger.Entry?

typealias LogFrontend = (KClass<*>) -> (Logger.Entry, String?) -> Unit

private val defaultFrontEnds by lazy {
    defaultLogFrontend(Logger::class)(Logger.Entry(Logger.Level.VERBOSE), "Using platform default log front end since no front end was defined")
    listOf(defaultLogFrontend)
}

@Suppress("NOTHING_TO_INLINE")
class Logger(@PublishedApi internal val from: KClass<*>, frontEnds: Collection<LogFrontend>, @PublishedApi internal val filters: Collection<LogFilter> = emptyList()) {

    @PublishedApi
    internal val frontends = (if (frontEnds.isNotEmpty()) frontEnds else defaultFrontEnds) .map { it(from) }

    enum class Level { VERBOSE, INFO, WARNING, ERROR }

    data class Entry(val level: Level, val ex: Throwable? = null, val meta: Map<String, Any> = emptyMap())

    @PublishedApi
    internal fun createEntry(level: Level, error: Throwable? = null, meta: Map<String, Any>): Entry? =
            filters.fold(Entry(level, error, meta)) { entry, filter -> filter(from, entry) ?: return null  }

    inline fun log(level: Level, error: Throwable? = null, meta: Map<String, Any> = emptyMap(), msgCreator: () -> String? = { null }) {
        val entry = createEntry(level, error, meta) ?: return
        val msg = msgCreator()
        frontends.forEach { it(entry, msg) }
    }

    inline fun verbose(msgCreator: () -> String) = log(Level.VERBOSE) { msgCreator() }

    inline fun info(msgCreator: () -> String) = log(Level.INFO, msgCreator = msgCreator)

    inline fun warning(ex: Throwable? = null, msgCreator: () -> String) = log(Level.WARNING, ex) { msgCreator() }
    inline fun warning(ex: Throwable) = log(Level.WARNING, ex)

    inline fun error(ex: Throwable? = null, msgCreator: () -> String) = log(Level.ERROR, ex) { msgCreator() }
    inline fun error(ex: Throwable) = log(Level.ERROR, ex)

    companion object {
        inline fun <reified T: Any> from(frontends: Collection<LogFrontend>, filters: Collection<LogFilter> = emptyList()): Logger = Logger(T::class, frontends, filters)
    }
}
