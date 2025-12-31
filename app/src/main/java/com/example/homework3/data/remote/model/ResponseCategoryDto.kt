package com.example.homework3.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ResponseCategoryDto (
    val id: String,
    val name: String,
    val children: List<ResponseCategoryDto>?
)