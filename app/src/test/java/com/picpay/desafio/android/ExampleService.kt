package com.picpay.desafio.android

import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.service.PicPayService

class ExampleService(
    private val service: PicPayService
) {

    fun example(): List<User> {
        val users = service.getUsers().execute()

        return users.body() ?: emptyList()
    }
}