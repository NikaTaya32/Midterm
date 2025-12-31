package com.example.homework3.domain.usecase

import com.example.homework3.domain.common.Resource
import com.example.homework3.domain.common.Resource.*
import com.example.homework3.domain.model.Category
import com.example.homework3.domain.repository.CategoryRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(query: String): Flow<Resource<List<Category>>> {
        return repository.getCategories().map { resource ->
            when (resource) {
                is Success -> {
                    val filteredList = if (query.isEmpty()) {
                        emptyList()
                    } else {
                        resource.data.filter { it.name.contains(query, ignoreCase = true) }
                    }
                    Success(filteredList)
                }
                is Error -> Error(resource.message)
                is Loader -> Loader(resource.isLoading)
            }
        }
    }
}