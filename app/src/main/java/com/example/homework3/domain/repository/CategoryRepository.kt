package com.example.homework3.domain.repository

import com.example.homework3.domain.common.Resource
import com.example.homework3.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getCategories(): Flow<Resource<List<Category>>>
}