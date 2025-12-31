package com.example.homework3.presentation.screen.category

import com.example.homework3.presentation.screen.category.model.CategoryModel

data class CategoryState(
    val isLoading: Boolean = false,
    val categories: List<CategoryModel> = emptyList(),
    val searchQuery: String = ""
)