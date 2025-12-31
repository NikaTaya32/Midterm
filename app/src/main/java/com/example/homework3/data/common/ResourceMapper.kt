package com.example.homework3.data.common

import com.example.homework3.domain.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <Domain, Dto> Flow<Resource<Domain>>.asResource(
    onSuccess: (Domain) -> Dto
): Flow<Resource<Dto>> {
    return this.map { resource ->
        when(resource) {
            is Resource.Error -> Resource.Error(message =  resource.message)
            is Resource.Loader -> Resource.Loader(isLoading =  resource.isLoading)
            is Resource.Success -> Resource.Success(data = onSuccess(resource.data))
        }
    }
}