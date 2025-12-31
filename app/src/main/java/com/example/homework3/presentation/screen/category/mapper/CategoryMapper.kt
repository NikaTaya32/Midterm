package com.example.homework3.presentation.screen.category.mapper

import com.example.homework3.domain.model.Category
import com.example.homework3.presentation.screen.category.model.CategoryModel

fun Category.toPresentation() : CategoryModel {
    return CategoryModel(
        id = id,
        name = name,
        depth = depth
    )
}