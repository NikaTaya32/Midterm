package com.example.homework3.data.mapper

import com.example.homework3.data.remote.model.ResponseCategoryDto
import com.example.homework3.domain.model.Category

fun List<ResponseCategoryDto>.flatten(depth: Int = 0): List<Category> {
    val result = mutableListOf<Category>()
    this.forEach { dto ->
        result.add(
            Category(
                id = dto.id,
                name = dto.name,
                depth = depth
            )
        )
        dto.children?.let {
            result.addAll(it.flatten(depth + 1))
        }
    }
    return result
}