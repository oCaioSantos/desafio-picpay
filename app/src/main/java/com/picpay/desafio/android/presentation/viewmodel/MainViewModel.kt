package com.picpay.desafio.android.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.domain.usecase.GetUsersUseCase
import com.picpay.desafio.android.presentation.stateholder.MainStateHolder
import kotlinx.coroutines.launch

class MainViewModel(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    var uiState = MutableLiveData<MainStateHolder>(MainStateHolder.Loading)
        private set

    init {
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
            val result = getUsersUseCase()
            when {
                result.isSuccess -> {
                    uiState.value = MainStateHolder.Success(
                        data = result.getOrNull() ?: emptyList()
                    )
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