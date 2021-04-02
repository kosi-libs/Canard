package org.kodein.log

public fun LogFrontend.withShortPackages(): LogFrontend = withShortPackageKeepLast(0)

public fun LogFrontend.withShortPackageKeepLast(count: Int): LogFrontend = LogFrontend { tag ->
    val pkgs = tag.pkg.split(".")
    val shortPkg = pkgs
        .mapIndexed { i, s -> if (i < pkgs.count() - count) s[0].toString() else s }
        .joinToString(".")
    getReceiverFor(Logger.Tag(shortPkg, tag.name))
}

public fun LogFrontend.withShortPackageShortenFirst(count: Int): LogFrontend = LogFrontend { tag ->
    val pkgs = tag.pkg.split(".")
    val shortPkg = pkgs
        .mapIndexed { i, s -> if (i < count) s[0].toString() else s }
        .joinToString(".")
    getReceiverFor(Logger.Tag(shortPkg, tag.name))
}
