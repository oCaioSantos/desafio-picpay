package com.picpay.desafio.android.data.repository.remote

import com.picpay.desafio.android.domain.service.PicPayService
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.enums.ResourceType
import com.picpay.desafio.android.domain.exception.BadParametersException
import com.picpay.desafio.android.domain.exception.ForbbidenException
import com.picpay.desafio.android.domain.exception.ResourceNotFoundException
import com.picpay.desafio.android.domain.exception.ServerDownException
import com.picpay.desafio.android.domain.exception.UnauthorizedException
import com.picpay.desafio.android.domain.exception.UnknowErrorException
import com.picpay.desafio.android.domain.repository.UserRepository
import retrofit2.HttpException

class UserRepositoryImpl(
    private val picPayService: PicPayService
) : UserRepository {

    override suspend fun getUsers(): Result<List<User>> {
        return try {
            val response = picPayService.getUsers()
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                handleHttpError(response.code())
            }
        } catch (e: HttpException) {
            handleHttpError(e.code())
        } catch (e: Exception) {
            Result.failure(UnknowErrorException())
        }
    }

    private fun handleHttpError(code: Int): Result<List<User>> {
        val resource = ResourceType.USER
        val exception = when (code) {
            400 -> BadParametersException()
            401 -> UnauthorizedException()
            403 -> ForbbidenException()
            404 -> ResourceNotFoundException(resource.displayName)
            in 500..599 -> ServerDownException()
            else -> UnknowErrorException()
        }
        return Result.failure(exception)
    }

}