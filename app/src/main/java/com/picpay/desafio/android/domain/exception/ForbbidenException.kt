package com.picpay.desafio.android.domain.exception

class ForbbidenException : Exception() {
    override val message: String?
        get() = "Acesso não permitido a este recurso."
}