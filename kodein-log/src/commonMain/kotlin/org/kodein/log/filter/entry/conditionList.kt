package org.kodein.log.filter.entry

import org.kodein.log.LogFilter
import org.kodein.log.Logger

/**
 * Defines whether a Log.Entry should be DISPLAYED or IGNORED
 */
public enum class FilterBehavior { DISPLAY, IGNORE }

public sealed class Condition {
    internal abstract val behavior: FilterBehavior
    internal abstract fun verifies(tag: Logger.Tag): Boolean

    public data class IsTag(val tag: Logger.Tag, override val behavior: FilterBehavior) : Condition() {
        override fun verifies(tag: Logger.Tag) = tag == this.tag
    }

    public data class IsPkg(val pkg: String, override val behavior: FilterBehavior, val eagerFilter: Boolean = true) : Condition() {
        override fun verifies(tag: Logger.Tag): Boolean = when(eagerFilter) {
            true -> tag.pkg == this.pkg
            false -> tag.pkg.startsWith(this.pkg)
        }
    }
}

public fun conditionList(default: FilterBehavior, list: Iterable<Condition>): LogFilter = LogFilter filter@ { tag, entry ->
    list.filter { it.verifies(tag) }.forEach {
        return@filter when (it.behavior) {
            FilterBehavior.DISPLAY -> entry
            else -> null
        }
    }
    return@filter when (default) {
        FilterBehavior.DISPLAY -> entry
        else -> null
    }
}


public fun allowList(tags: Collection<Logger.Tag> = emptyList(), packages: Collection<String> = emptyList()): LogFilter =
    conditionList(
        FilterBehavior.IGNORE,
        tags.map { Condition.IsTag(it, FilterBehavior.DISPLAY) } + packages.map { Condition.IsPkg(it, FilterBehavior.DISPLAY) }
    )
public fun blockList(tags: Collection<Logger.Tag> = emptyList(), packages: Collection<String> = emptyList()): LogFilter =
    conditionList(
        FilterBehavior.DISPLAY,
        tags.map { Condition.IsTag(it, FilterBehavior.IGNORE) } + packages.map { Condition.IsPkg(it, FilterBehavior.IGNORE) }
    )