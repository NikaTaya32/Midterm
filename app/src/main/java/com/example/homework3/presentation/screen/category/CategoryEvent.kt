package com.example.homework3.presentation.screen.category

sealed class CategoryEvent {
    data class SearchQueryChanged(val query: String) : CategoryEvent()
}