package com.example.homework3.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework3.domain.common.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<State, Event ,SideEffect>(
    initialState: State
) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state.asStateFlow()

    private val _sideEffect by lazy { Channel<SideEffect>() }
    val sideEffect: Flow<SideEffect> by lazy { _sideEffect.receiveAsFlow() }

    protected fun updateState(update: (State) -> State) {
        _state.value = update(_state.value)
    }

    protected fun emitSideEffect(sideEffect: SideEffect) {
        viewModelScope.launch(Dispatchers.Main.immediate) {
            _sideEffect.send(sideEffect)
        }
    }

    open fun onEvent(event: Event) = Unit

    protected fun <T : Any> handleResponse(
        apiCall: suspend () -> Flow<Resource<T>>,
        onSuccess: (T) -> Unit,
        onError: ((String) -> Unit)? = null,
        onLoading: (Resource.Loader<T>) -> Unit,
    ) {
        viewModelScope.launch {
            apiCall.invoke().collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        onError?.invoke(resource.message)
                    }

                    is Resource.Loader -> {
                        onLoading.invoke(resource)
                    }

                    is Resource.Success -> {
                        onSuccess.invoke(resource.data)
                    }
                }
            }
        }
    }
}