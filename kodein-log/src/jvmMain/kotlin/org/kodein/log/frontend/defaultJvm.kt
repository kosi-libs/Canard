package org.kodein.log.frontend

import org.kodein.log.LogFrontend

actual val defaultLogFrontend: LogFrontend get() = SLF4JFrontend
