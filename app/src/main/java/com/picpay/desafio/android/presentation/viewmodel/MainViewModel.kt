package com.picpay.desafio.android.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.usecase.GetUsersUseCase
import com.picpay.desafio.android.presentation.stateholder.MainStateHolder
import kotlinx.coroutines.launch

class MainViewModel(
    val savedStateHandle: SavedStateHandle,
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    companion object {
        const val KEY = "users"
    }

    var uiState = MutableLiveData<MainStateHolder>()
        private set

    fun loadUsers() {
        val cachedUsers = savedStateHandle.get<List<User>>(KEY)
        if (cachedUsers != null) {
            uiState.value = MainStateHolder.Success(data = cachedUsers)
        } else {
            viewModelScope.launch {
                uiState.value = MainStateHolder.Loading
                val result = getUsersUseCase()
                when {
                    result.isSuccess -> {
                        val userList = result.getOrNull() ?: emptyList()
                        uiState.value = MainStateHolder.Success(
                            data = userList
                        )
                        savedStateHandle[KEY] = userList
                    }

                    result.isFailure -> {
                        uiState.value = MainStateHolder.Error(
                            message = result.exceptionOrNull()?.message ?: ""
                        )
                    }
                }
            }
        }
    }

}