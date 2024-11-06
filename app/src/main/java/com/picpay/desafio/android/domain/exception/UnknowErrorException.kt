package com.picpay.desafio.android.domain.exception

class UnknowErrorException : Exception() {
    override val message: String
        get() = "Ocorreu um erro desconhecido."
}