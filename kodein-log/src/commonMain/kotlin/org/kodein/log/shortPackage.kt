package org.kodein.log

/**
 * Shrink every [Logger.Tag] package name to its first letter
 */
public fun LogFrontend.withShortPackages(): LogFrontend = withShortPackageKeepLast(0)

/**
 * Shrink [Logger.Tag] package name to its first letter, except for the last one(s)
 *
 * @param count number of package name to keep at the end of the path
 */
public fun LogFrontend.withShortPackageKeepLast(count: Int): LogFrontend = LogFrontend { tag ->
    val pkgs = tag.pkg.split(".")
    val shortPkg = pkgs
        .mapIndexed { i, s -> if (i < pkgs.count() - count) s[0].toString() else s }
        .joinToString(".")
    getReceiverFor(Logger.Tag(shortPkg, tag.name))
}

/**
 * Shrink [Logger.Tag] package name to its first letter
 *
 * @param count number of package name to shrink at the beginning of the path
 */
public fun LogFrontend.withShortPackageShortenFirst(count: Int): LogFrontend = LogFrontend { tag ->
    val pkgs = tag.pkg.split(".")
    val shortPkg = pkgs
        .mapIndexed { i, s -> if (i < count) s[0].toString() else s }
        .joinToString(".")
    getReceiverFor(Logger.Tag(shortPkg, tag.name))
}
