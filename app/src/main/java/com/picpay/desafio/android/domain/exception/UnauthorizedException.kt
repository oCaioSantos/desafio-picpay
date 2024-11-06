package com.picpay.desafio.android.domain.exception

class UnauthorizedException : Exception() {
    override val message: String
        get() = "Acesso não autorizado."
}