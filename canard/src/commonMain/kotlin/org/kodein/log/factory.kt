package org.kodein.log

import org.kodein.log.frontend.defaultLogFrontend
import kotlin.reflect.KClass

/**
 * Factory for [Logger] that aims to carry a common configuration for all the [Logger]s of your application.
 */
public class LoggerFactory(private val frontends: Collection<LogFrontend>, private val filters: List<LogFilter> = emptyList(), private val mappers: List<LogMapper> = emptyList()) {
    public constructor(vararg frontends: LogFrontend, filters: List<LogFilter> = emptyList(), mappers: List<LogMapper> = emptyList()): this(frontends.toList(), filters, mappers)

    /**
     * Create a now [Logger] from the factory configuration
     *
     * @param tag [Logger]s identifier
     */
    public fun newLogger(tag: Logger.Tag): Logger = Logger(tag, frontends, filters, mappers)

    public fun add(vararg frontends: LogFrontend): LoggerFactory = LoggerFactory(this.frontends + frontends, filters)

    public fun append(vararg filters: LogFilter): LoggerFactory = LoggerFactory(frontends, this.filters + filters)
    public fun prepend(vararg filters: LogFilter): LoggerFactory = LoggerFactory(frontends, filters.toList() + this.filters)

    public fun append(vararg mappers: LogMapper): LoggerFactory = LoggerFactory(frontends, filters, this.mappers + mappers)
    public fun prepend(vararg mappers: LogMapper): LoggerFactory = LoggerFactory(frontends, filters, mappers.toList() + this.mappers)

    public companion object {
        /**
         * Get a [LoggerFactory] with [defaultLogFrontend] as preconfigured [LogFrontend]
         */
        public val default: LoggerFactory = LoggerFactory(listOf(defaultLogFrontend))
    }
}

/**
 * Create new [Logger] from a [KClass]
 *
 * @param cls [KClass] mapped to a [Tag]
 */
public fun LoggerFactory.newLogger(cls: KClass<*>): Logger = newLogger(Logger.Tag(cls))

/**
 * Create new [Logger] from a package/name pair mapped to a [Tag]
 *
 * @param pkg package name
 * @param name class name
 */
public fun LoggerFactory.newLogger(pkg: String, name: String): Logger = newLogger(Logger.Tag(pkg, name))

/**
 * Create a [Logger]
 * [Tag] is mapped from the type parameter [KClass]
 */
public inline fun <reified T> LoggerFactory.newLogger(): Logger = newLogger(T::class)

/**
 * Create a [Logger]
 * [Tag] is mapped from type receiver [T]
 */
public inline fun <reified T> T.newLogger(factory: LoggerFactory): Logger = factory.newLogger(T::class)
