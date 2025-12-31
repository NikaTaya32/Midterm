package com.example.homework3.data.remote.api_service

import com.example.homework3.data.remote.model.ResponseCategoryDto
import retrofit2.Response
import retrofit2.http.GET

interface CategoriesApiService {
    @GET("0c08be03-49c2-493b-951c-6ba8a397dc72")
    suspend fun getCategories(): Response<List<ResponseCategoryDto>>
}

