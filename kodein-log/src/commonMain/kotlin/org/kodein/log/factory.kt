package org.kodein.log

import org.kodein.log.frontend.defaultLogFrontend
import kotlin.reflect.KClass


public interface LoggerFactory {
    public fun newLogger(tag: Logger.Tag): Logger

    public companion object {
        public operator fun invoke(factory: (Logger.Tag) -> Logger): LoggerFactory = object : LoggerFactory {
            override fun newLogger(tag: Logger.Tag) = factory(tag)
        }

        public operator fun invoke(frontends: Collection<LogFrontend>, filters: Collection<LogFilter> = emptyList()): LoggerFactory = LoggerFactory {
            Logger(it, frontends, filters)
        }

        public val default: LoggerFactory = LoggerFactory { Logger(it, listOf(defaultLogFrontend)) }
    }
}

public fun LoggerFactory.newLogger(cls: KClass<*>): Logger = newLogger(Logger.Tag(cls))
public fun LoggerFactory.newLogger(pkg: String, name: String): Logger = newLogger(Logger.Tag(pkg, name))

public inline fun <reified T> LoggerFactory.newLogger(): Logger = newLogger(T::class)
public inline fun <reified T> T.newLogger(factory: LoggerFactory): Logger = factory.newLogger(T::class)
