package com.picpay.desafio.android.presentation.stateholder

import com.picpay.desafio.android.domain.model.User

sealed class MainStateHolder {
    data object Loading : MainStateHolder()
    data class Success(
        val data: List<User>
    ) : MainStateHolder()

    data class Error(
        val message: String
    ) : MainStateHolder()
}