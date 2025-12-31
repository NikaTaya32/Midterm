package com.example.homework3.domain.common

sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val message: String) : Resource<T>()
    data class Loader<T>(val isLoading: Boolean) : Resource<T>()
}