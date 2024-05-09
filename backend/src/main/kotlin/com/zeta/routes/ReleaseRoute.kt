package com.zeta.routes

import com.zeta.Routes
import com.zeta.data.OutputNumberResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.random.Random

fun Routing.releaseRoute() {
    get(Routes.ROUTE_RELEASE) {
        call.respond(
            HttpStatusCode.OK,
            getPrimeNumber()
        )
    }
}

fun getPrimeNumber(): OutputNumberResponse {
    val number = (1..100).random()
    return OutputNumberResponse(
        number
    )
}

