package org.kodein.log.frontend

import org.kodein.log.LogFrontend

actual val defaultLogFrontend: LogFrontend get() = consoleFrontend
