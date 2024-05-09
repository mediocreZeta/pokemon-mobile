package com.zeta.data

import kotlinx.serialization.Serializable


@Serializable
data class ErrorResponse(
    val status: Int,
    val error: String,
    val detail: String,
    val path: String
)


