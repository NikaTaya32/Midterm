package com.example.homework3.data.common

import com.example.homework3.domain.common.Resource
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class HandleResponse @Inject constructor() {
    fun <T> safeApiCall(call: suspend () -> Response<T>) = flow {
        emit(Resource.Loader(isLoading = true))
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    emit(Resource.Success(body))
                }
            } else {
                val error = response.errorBody()?.string()!!
                emit(Resource.Error(error))
            }
        } catch (e: IOException) {
            emit(Resource.Error("Network error: ${e.message.orEmpty()}"))
        } catch (e: HttpException) {
            emit(Resource.Error("HTTP error: ${e.message.orEmpty()}"))
        } catch (e: IllegalStateException) {
            emit(Resource.Error(message = e.message.orEmpty()))
        } catch (e: Exception) {
            emit(Resource.Error("An unexpected error occurred: ${e.message.orEmpty()}"))
        }
    }
}