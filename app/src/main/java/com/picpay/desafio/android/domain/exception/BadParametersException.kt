package com.picpay.desafio.android.domain.exception

class BadParametersException : Exception() {
    override val message: String
        get() = "Verifique os dados informados."
}