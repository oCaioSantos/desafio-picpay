package com.picpay.desafio.android.domain.exception

class ResourceNotFoundException(
    private val resourceName: String
) : Exception() {
    override val message: String?
        get() = "$resourceName não encontrado."
}