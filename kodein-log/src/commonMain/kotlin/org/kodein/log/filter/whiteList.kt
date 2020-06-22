package org.kodein.log.filter

import org.kodein.log.LogFilter
import org.kodein.log.Logger
import org.kodein.log.platformPackageName
import kotlin.reflect.KClass


public sealed class Condition {
    internal abstract val display: Boolean
    internal abstract fun verifies(tag: Logger.Tag): Boolean

    public data class IsTag(val tag: Logger.Tag, override val display: Boolean) : Condition() {
        override fun verifies(tag: Logger.Tag) = tag == this.tag
    }

    public data class IsPkg(val pkg: String, override val display: Boolean) : Condition() {
        override fun verifies(tag: Logger.Tag): Boolean = tag.pkg == this.pkg
    }
}

public fun conditionList(default: Boolean, list: Iterable<Condition>): LogFilter = filter@ { tag, entry ->
    list.forEach {
        if (it.verifies(tag)) {
            return@filter if (it.display) entry else null
        }
    }
    return@filter if (default) entry else null
}
