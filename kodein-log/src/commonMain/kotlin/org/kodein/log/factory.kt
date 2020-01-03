package org.kodein.log

import org.kodein.log.frontend.defaultLogFrontend
import kotlin.reflect.KClass


interface LoggerFactory {
    fun newLogger(from: KClass<*>): Logger

    companion object {
        operator fun invoke(factory: (KClass<*>) -> Logger) = object : LoggerFactory {
            override fun newLogger(from: KClass<*>) = factory(from)
        }

        operator fun invoke(frontends: Collection<LogFrontend>, filters: Collection<LogFilter> = emptyList()) = LoggerFactory {
            Logger(it, frontends, filters)
        }

        val default = LoggerFactory { Logger(it, listOf(defaultLogFrontend)) }
    }
}

inline fun <reified T> LoggerFactory.newLogger() = newLogger(T::class)
