package com.picpay.desafio.android.domain.exception

class ServerDownException : Exception() {
    override val message: String
        get() = "Estamos enfrentando problemas com o servidor."
}