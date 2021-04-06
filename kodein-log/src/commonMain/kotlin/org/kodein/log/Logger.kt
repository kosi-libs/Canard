package org.kodein.log

import org.kodein.log.Logger.Level.*
import org.kodein.log.frontend.defaultLogFrontend
import kotlin.reflect.KClass

/**
 * SAM interface that helps to restrain or enhance logs
 */
public fun interface LogFilter {
    /**
     * Filter each log on their [Logger.Tag] and [Logger.Entry]
     *
     * @return nullable [Logger.Entry] ; if null the log will be ignore
     */
    public fun filter(tag: Logger.Tag, entry: Logger.Entry): Logger.Entry?
}

/**
 * SAM interface that helps altering each log depending on the [filter] implementation.
 */
public fun interface LogMapper {
    /**
     * Transform each log message
     *
     * @param tag [Logger.Tag] of the current log
     * @param entry [Logger.Entry] of the current log
     * @param message [String] that might be transform
     *
     * @return [String] that will be send to the log outputs
     */
    public fun filter(tag: Logger.Tag, entry: Logger.Entry, message: String): String
}

/**
 * SAM interface that helps defining what to do with a given [Logger.Entry]
 *
 * Usually used in [LogFrontend] implementations
 */
public fun interface LogReceiver {
    public fun receive(entry: Logger.Entry, message: String?)
}

/**
 *
 */
public fun interface LogFrontend {
    /**
     * Create a [LogReceiver] that will be used to carry every [Logger.Entry]
     */
    public fun getReceiverFor(tag: Logger.Tag): LogReceiver
}

private val defaultFrontEnds by lazy {
    defaultLogFrontend
            .getReceiverFor(Logger.Tag(Logger::class))
            .receive(Logger.Entry(DEBUG), "Using platform default log front end since no front end was defined")
    listOf(defaultLogFrontend)
}

@Suppress("NOTHING_TO_INLINE")
/**
 * Logger
 */
public class Logger(
    private val tag: Tag, frontEnds: Collection<LogFrontend>,
    private val filters: List<LogFilter> = emptyList(),
    private val mappers: List<LogMapper> = emptyList()
) {

    /**
     * Log entry identifier with a package and a name
     */
    public data class Tag(val pkg: String, val name: String) {
        public constructor(cls: KClass<*>) : this(cls.platformPackageName, cls.platformSimpleName)
        override fun toString(): String = "$pkg.$name"
    }

    @PublishedApi
    internal val frontends: List<LogReceiver> = (frontEnds.ifEmpty { defaultFrontEnds }) .map { it.getReceiverFor(tag) }

    /**
     * Log severity
     */
    public enum class Level(public val severity: Int) { DEBUG(0), INFO(1), WARNING(2), ERROR(3) }

    /**
     * Metadata that helps describe log messages
     */
    public data class Entry(val level: Level, val ex: Throwable? = null, val meta: Map<String, Any> = emptyMap(), val instant: Instant = now())

    @PublishedApi
    internal fun createEntry(level: Level, error: Throwable? = null, meta: Map<String, Any>): Entry? =
            filters.fold(Entry(level, error, meta)) { entry, filter -> filter.filter(tag, entry) ?: return null  }

    @PublishedApi
    internal fun String.filterMessage(entry: Entry): String =
        mappers.fold(this) { message, filter -> filter.filter(tag, entry, message) }

    /**
     * Create a [Logger.Entry] with the proper metadata and pair it with a given message
     */
    public inline fun log(level: Level, error: Throwable? = null, meta: Map<String, Any> = emptyMap(), msgCreator: () -> String? = { null }) {
        val entry = createEntry(level, error, meta) ?: return
        val msg = msgCreator()?.filterMessage(entry)
        frontends.forEach { it.receive(entry, msg) }
    }

    /**
     * Log a message with [DEBUG] severity
     */
    public inline fun debug(msgCreator: () -> String) { log(level = DEBUG, msgCreator = msgCreator) }

    /**
     * Log a message with [INFO] severity
     */
    public inline fun info(msgCreator: () -> String) { log(level = INFO, msgCreator = msgCreator) }

    /**
     * Log an exception and a message with [WARNING] severity
     */
    public inline fun warning(ex: Throwable? = null, msgCreator: () -> String) { log(level = WARNING, error = ex, msgCreator = msgCreator) }
    /**
     * Log an exception with [WARNING] severity
     */
    public inline fun warning(ex: Throwable) { log(level = WARNING, error = ex) }

    /**
     * Log an exception and a message with [ERROR] severity
     */
    public inline fun error(ex: Throwable? = null, msgCreator: () -> String) { log(level = ERROR, error = ex, msgCreator = msgCreator) }
    /**
     * Log an exception with [ERROR] severity
     */
    public inline fun error(ex: Throwable) { log(level = ERROR, error = ex) }

    public companion object {
        /**
         * Create a [Logger] with the given configuration
         */
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

        /**
         * Create a [Logger] with the given configuration
         */
        public inline fun <reified T: Any> from(
            vararg frontends: LogFrontend,
            filters: List<LogFilter> = emptyList(),
            mappers: List<LogMapper> = emptyList(),
        ): Logger = from<T>(frontends.toList(), filters, mappers)
    }
}
