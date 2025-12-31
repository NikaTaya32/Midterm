package com.example.homework3.presentation.screen.category

import androidx.lifecycle.viewModelScope
import com.example.homework3.domain.usecase.GetCategoriesUseCase
import com.example.homework3.presentation.common.BaseViewModel
import com.example.homework3.presentation.screen.category.CategorySideEffect.*
import com.example.homework3.presentation.screen.category.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : BaseViewModel<CategoryState, CategoryEvent, CategorySideEffect>(CategoryState()) {

    init {
        observeSearchQuery()
    }

    override fun onEvent(event: CategoryEvent) {
        when (event) {
            is CategoryEvent.SearchQueryChanged -> updateState { it.copy(searchQuery = event.query) }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        viewModelScope.launch {
            state
                .map { it.searchQuery }
                .debounce(500)
                .distinctUntilChanged()
                .collect { query ->
                    fetchCategories(query)
                }
        }
    }

    private fun fetchCategories(query: String) {
        handleResponse(
            apiCall = { getCategoriesUseCase(query) },
            onSuccess = { categories ->
                updateState { state ->
                    state.copy(
                        isLoading = false,
                        categories = categories.map { it.toPresentation() }
                    )
                }
            },
            onLoading = {
                if (query.isBlank()) return@handleResponse
                updateState { it.copy(isLoading = true) }
            },
            onError = { errorMessage ->
                updateState { it.copy(isLoading = false) }
                emitSideEffect(ShowError(errorMessage))
            }
        )
    }
}