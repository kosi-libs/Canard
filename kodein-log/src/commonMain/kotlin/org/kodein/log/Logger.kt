package org.kodein.log

import org.kodein.log.frontend.defaultLogFrontend
import kotlin.reflect.KClass


public typealias LogFilter = (Logger.Tag, Logger.Entry) -> Logger.Entry?

public typealias LogFrontend = (Logger.Tag) -> (Logger.Entry, String?) -> Unit

private val defaultFrontEnds by lazy {
    defaultLogFrontend(Logger.Tag(Logger::class))(Logger.Entry(Logger.Level.VERBOSE), "Using platform default log front end since no front end was defined")
    listOf(defaultLogFrontend)
}

@Suppress("NOTHING_TO_INLINE")
public class Logger(@PublishedApi internal val tag: Tag, frontEnds: Collection<LogFrontend>, @PublishedApi internal val filters: Collection<LogFilter> = emptyList()) {

    public data class Tag(val pkg: String, val name: String) {
        public constructor(cls: KClass<*>) : this(cls.platformPackageName, cls.platformSimpleName)

        override fun toString(): String = "$pkg.$name"
    }

    @PublishedApi
    internal val frontends = (if (frontEnds.isNotEmpty()) frontEnds else defaultFrontEnds) .map { it(tag) }

    public enum class Level { VERBOSE, INFO, WARNING, ERROR }

    public data class Entry(val level: Level, val ex: Throwable? = null, val meta: Map<String, Any> = emptyMap())

    @PublishedApi
    internal fun createEntry(level: Level, error: Throwable? = null, meta: Map<String, Any>): Entry? =
            filters.fold(Entry(level, error, meta)) { entry, filter -> filter(tag, entry) ?: return null  }

    public inline fun log(level: Level, error: Throwable? = null, meta: Map<String, Any> = emptyMap(), msgCreator: () -> String? = { null }) {
        val entry = createEntry(level, error, meta) ?: return
        val msg = msgCreator()
        frontends.forEach { it(entry, msg) }
    }

    public inline fun verbose(msgCreator: () -> String) { log(Level.VERBOSE) { msgCreator() } }

    public inline fun info(msgCreator: () -> String) { log(Level.INFO, msgCreator = msgCreator) }

    public inline fun warning(ex: Throwable? = null, msgCreator: () -> String) { log(Level.WARNING, ex) { msgCreator() } }
    public inline fun warning(ex: Throwable) { log(Level.WARNING, ex) }

    public inline fun error(ex: Throwable? = null, msgCreator: () -> String) { log(Level.ERROR, ex) { msgCreator() } }
    public inline fun error(ex: Throwable) { log(Level.ERROR, ex) }

    public companion object {
        public inline fun <reified T: Any> from(frontends: Collection<LogFrontend>, filters: Collection<LogFilter> = emptyList()): Logger = Logger(Logger.Tag(T::class), frontends, filters)
    }
}
