package org.kodein.log

import org.kodein.log.Logger.Level.*
import org.kodein.log.frontend.defaultLogFrontend
import kotlin.reflect.KClass


public fun interface LogFilter {
    public fun filter(tag: Logger.Tag, entry: Logger.Entry): Logger.Entry?
}

public fun interface LogMapper {
    public fun filter(tag: Logger.Tag, entry: Logger.Entry, message: String): String
}

public fun interface LogReceiver {
    public fun receive(entry: Logger.Entry, message: String?)
}

public fun interface LogFrontend {
    public fun getReceiverFor(tag: Logger.Tag): LogReceiver
}

private val defaultFrontEnds by lazy {
    defaultLogFrontend
            .getReceiverFor(Logger.Tag(Logger::class))
            .receive(Logger.Entry(DEBUG), "Using platform default log front end since no front end was defined")
    listOf(defaultLogFrontend)
}

@Suppress("NOTHING_TO_INLINE")
public class Logger(
    private val tag: Tag, frontEnds: Collection<LogFrontend>,
    private val filters: List<LogFilter> = emptyList(),
    private val mappers: List<LogMapper> = emptyList()
) {

    public data class Tag(val pkg: String, val name: String) {
        public constructor(cls: KClass<*>) : this(cls.platformPackageName, cls.platformSimpleName)
        override fun toString(): String = "$pkg.$name"
    }

    @PublishedApi
    internal val frontends: List<LogReceiver> = (frontEnds.ifEmpty { defaultFrontEnds }) .map { it.getReceiverFor(tag) }

    public enum class Level(public val severity: Int) { DEBUG(0), INFO(1), WARNING(2), ERROR(3) }

    public data class Entry(val level: Level, val ex: Throwable? = null, val meta: Map<String, Any> = emptyMap(), val instant: Instant = now())

    @PublishedApi
    internal fun createEntry(level: Level, error: Throwable? = null, meta: Map<String, Any>): Entry? =
            filters.fold(Entry(level, error, meta)) { entry, filter -> filter.filter(tag, entry) ?: return null  }

    @PublishedApi
    internal fun String.filterMessage(entry: Entry): String =
        mappers.fold(this) { message, filter -> filter.filter(tag, entry, message) }

    public inline fun log(level: Level, error: Throwable? = null, meta: Map<String, Any> = emptyMap(), msgCreator: () -> String? = { null }) {
        val entry = createEntry(level, error, meta) ?: return
        val msg = msgCreator()?.filterMessage(entry)
        frontends.forEach { it.receive(entry, msg) }
    }

    public inline fun debug(msgCreator: () -> String) { log(level = DEBUG, msgCreator = msgCreator) }

    public inline fun info(msgCreator: () -> String) { log(level = INFO, msgCreator = msgCreator) }

    public inline fun warning(ex: Throwable? = null, msgCreator: () -> String) { log(level = WARNING, error = ex, msgCreator = msgCreator) }
    public inline fun warning(ex: Throwable) { log(level = WARNING, error = ex) }

    public inline fun error(ex: Throwable? = null, msgCreator: () -> String) { log(level = ERROR, error = ex, msgCreator = msgCreator) }
    public inline fun error(ex: Throwable) { log(level = ERROR, error = ex) }

    public companion object {
        public inline fun <reified T: Any> from(
            frontends: Collection<LogFrontend>,
            filters: List<LogFilter> = emptyList(),
            mappers: List<LogMapper> = emptyList(),
        ): Logger = Logger(
            tag = Tag(T::class),
            frontEnds = frontends,
            filters = filters,
            mappers = mappers
        )

        public inline fun <reified T: Any> from(
            vararg frontends: LogFrontend,
            filters: List<LogFilter> = emptyList(),
            mappers: List<LogMapper> = emptyList(),
        ): Logger = from<T>(frontends.toList(), filters, mappers)
    }
}
