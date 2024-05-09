package com.zeta.routes

import com.zeta.Routes
import com.zeta.data.ProbabilityResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.probabilityRoute() {
    get(Routes.ROUTE_CATCH) {
        call.respond(
            HttpStatusCode.OK,
            getProbability()
        )
    }
}

fun getProbability(): ProbabilityResponse {
    val probSuccess = listOf(true, false)
    return ProbabilityResponse(
        probSuccess.random()
    )
}

