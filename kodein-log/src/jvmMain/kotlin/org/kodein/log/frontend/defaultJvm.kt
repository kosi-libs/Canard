package org.kodein.log.frontend

import org.kodein.log.LogFrontend

public actual val defaultLogFrontend: LogFrontend get() = SLF4JFrontend
