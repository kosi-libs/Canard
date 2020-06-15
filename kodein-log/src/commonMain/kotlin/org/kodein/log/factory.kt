package org.kodein.log

import org.kodein.log.frontend.defaultLogFrontend
import kotlin.reflect.KClass


public interface LoggerFactory {
    public fun newLogger(from: KClass<*>): Logger

    public companion object {
        public operator fun invoke(factory: (KClass<*>) -> Logger): LoggerFactory = object : LoggerFactory {
            override fun newLogger(from: KClass<*>) = factory(from)
        }

        public operator fun invoke(frontends: Collection<LogFrontend>, filters: Collection<LogFilter> = emptyList()): LoggerFactory = LoggerFactory {
            Logger(it, frontends, filters)
        }

        public val default: LoggerFactory = LoggerFactory { Logger(it, listOf(defaultLogFrontend)) }
    }
}

public inline fun <reified T> LoggerFactory.newLogger(): Logger = newLogger(T::class)
public inline fun <reified T> T.newLogger(factory: LoggerFactory): Logger = factory.newLogger(T::class)
