package com.zeta.routes

import com.zeta.Routes
import com.zeta.data.ErrorResponse
import com.zeta.data.RenameResponse
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import org.junit.Test
import kotlin.test.assertEquals

class PokemonTest {

    @Test
    fun when_testRenameNullName_expect_errorStatus() {
        testApplication {
            val response = client.get(Routes.ROUTE_RENAME) {
                parameter("name", "")
                parameter("number", 3)
            }
            val output = ErrorResponse(
                status = 400,
                error = HttpStatusCode.BadRequest.description,
                detail = "Null or empty name parameter",
                path = Routes.ROUTE_RENAME
            )
            val expectedResponse =
                Json.encodeToString(ErrorResponse.serializer(), output)

            assertEquals(HttpStatusCode.BadRequest, response.status)
            assertEquals(expectedResponse, response.bodyAsText())
        }
    }

    @Test
    fun when_testRenameNullNumber_expect_errorStatus() {
        testApplication {
            val response = client.get(Routes.ROUTE_RENAME) {
                parameter("name", "mark")
                parameter("number", null)
            }
            val output = ErrorResponse(
                status = 400,
                error = HttpStatusCode.BadRequest.description,
                detail = "Given number cannot empty",
                path = Routes.ROUTE_RENAME
            )
            val expectedResponse =
                Json.encodeToString(ErrorResponse.serializer(), output)

            assertEquals(HttpStatusCode.BadRequest, response.status)
            assertEquals(expectedResponse, response.bodyAsText())
        }
    }

    @Test
    fun when_testRenameFirstTime_expect_success() {
        testApplication {
            //FIRST TIMER CHECK
            val response = client.get(Routes.ROUTE_RENAME) {
                parameter("name", "mark")
                parameter("number", 0)
            }
            val output = RenameResponse(
                "mark-0"
            )
            val expectedResponse =
                Json.encodeToString(RenameResponse.serializer(), output)

            assertEquals(HttpStatusCode.OK, response.status)
            assertEquals(expectedResponse, response.bodyAsText())
        }
    }

    @Test
    fun when_testRenameNotFirstTime_expect_success() {
        testApplication {
            //FIRST TIMER CHECK
            val response = client.get(Routes.ROUTE_RENAME) {
                parameter("name", "mark-1")
                parameter("number", 3)
            }
            val output = RenameResponse(
                "mark-2"
            )
            val expectedResponse =
                Json.encodeToString(RenameResponse.serializer(), output)

            assertEquals(HttpStatusCode.OK, response.status)
            assertEquals(expectedResponse, response.bodyAsText())
        }
    }

}