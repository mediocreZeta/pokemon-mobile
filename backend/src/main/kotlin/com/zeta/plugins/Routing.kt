package com.zeta.plugins

import com.zeta.routes.probabilityRoute
import com.zeta.routes.releaseRoute
import com.zeta.routes.renameRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        probabilityRoute()
        releaseRoute()
        renameRoute()
    }
}
