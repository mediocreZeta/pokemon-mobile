package com.zeta.routes

import com.zeta.Routes
import com.zeta.data.ErrorResponse
import com.zeta.data.RenameResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

val savedNumbers: MutableMap<Int, Long> = mutableMapOf()

fun Routing.renameRoute() {
    get(Routes.ROUTE_RENAME) {
        val nameParams = call.parameters["name"]
        val fibParams = call.parameters["number"]?.toIntOrNull()

        if (nameParams.isNullOrBlank()) {
            call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse(
                    status = 400,
                    error = HttpStatusCode.BadRequest.description,
                    detail = "Null or empty name parameter",
                    path = Routes.ROUTE_RENAME
                )
            )
            return@get
        }

        if (fibParams == null) {
            call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse(
                    status = 400,
                    error = HttpStatusCode.BadRequest.description,
                    detail = "Given number cannot empty",
                    path = Routes.ROUTE_RENAME
                )
            )
            return@get
        }

        savedNumbers.clear()
        call.respond(
            HttpStatusCode.OK,
            renameWithFib(nameParams, fibParams)
        )
    }
}


fun renameWithFib(name: String, n: Int): RenameResponse {
    val sb = StringBuilder()

    for (character in name) {
        if (character == '-') break
        sb.append(character)
    }

    val number = fib(n)
    sb.append("-$number")
    return RenameResponse(
        sb.toString()
    )
}

fun fib(n: Int): Long {
    if (n <= 1) return n.toLong()
    return fib(n - 1) + fib(n - 2);
}


