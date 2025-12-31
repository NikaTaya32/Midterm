package com.example.homework3.data.repository

import com.example.homework3.data.common.HandleResponse
import com.example.homework3.data.common.asResource
import com.example.homework3.data.mapper.flatten
import com.example.homework3.data.remote.api_service.CategoriesApiService
import com.example.homework3.domain.common.Resource
import com.example.homework3.domain.model.Category
import com.example.homework3.domain.repository.CategoryRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class CategoryRepositoryImpl @Inject constructor(
    private val apiService: CategoriesApiService,
    private val handleResponse: HandleResponse,
) : CategoryRepository {
    override suspend fun getCategories(): Flow<Resource<List<Category>>> {
        return handleResponse.safeApiCall {
            apiService.getCategories()
        }.asResource {
            it.flatten()
        }
    }
}