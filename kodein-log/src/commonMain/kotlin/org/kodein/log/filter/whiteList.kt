package org.kodein.log.filter

import org.kodein.log.LogFilter
import org.kodein.log.packageName
import kotlin.reflect.KClass


sealed class Condition {
    abstract val display: Boolean
    internal abstract fun verifies(cls: KClass<*>): Boolean
    data class IsClass(val cls: KClass<*>, override val display: Boolean) : Condition() {
        override fun verifies(cls: KClass<*>) = cls == this.cls
    }
    data class IsPackage(val packageName: String, override val display: Boolean) : Condition() {
        override fun verifies(cls: KClass<*>): Boolean = cls.packageName == this.packageName
    }
}

fun conditionList(default: Boolean, list: Iterable<Condition>): LogFilter = filter@ { from, entry ->
    list.forEach {
        if (it.verifies(from)) {
            return@filter if (it.display) entry else null
        }
    }
    return@filter if (default) entry else null
}
