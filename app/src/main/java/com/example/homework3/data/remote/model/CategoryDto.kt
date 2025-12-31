package com.example.homework3.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    val id: String,
    val name: String,
    val depth: Int
)