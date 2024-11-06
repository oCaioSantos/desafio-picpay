package com.picpay.desafio.android.domain.usecase

import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.repository.UserRepository

class GetUsersUseCase(
    private val usersRepository: UserRepository
) {

    suspend operator fun invoke(): Result<List<User>?> {
        return usersRepository.getUsers()
    }

}