package com.example.homework3.presentation.screen.category

sealed class CategorySideEffect {
    data class ShowError(val message: String) : CategorySideEffect()
}