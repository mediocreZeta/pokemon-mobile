package com.zeta.data

import kotlinx.serialization.Serializable

@Serializable
data class ProbabilityResponse(
    val is_success: Boolean
)

@Serializable
data class RenameResponse(
    val name: String
)

@Serializable
data class OutputNumberResponse(
    val number: Int
)
