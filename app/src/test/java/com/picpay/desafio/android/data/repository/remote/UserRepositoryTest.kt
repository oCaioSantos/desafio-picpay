package com.picpay.desafio.android.data.repository.remote

import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.domain.exception.ResourceNotFoundException
import com.picpay.desafio.android.domain.exception.UnknowErrorException
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.domain.service.PicPayService
import com.picpay.desafio.android.fixtures.UserFixtures
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class UserRepositoryTest {

    @Mock
    private lateinit var picPayService: PicPayService

    @Mock
    private lateinit var userRepository: UserRepository

    @Before
    fun setup() {
        userRepository = UserRepositoryImpl(
            picPayService
        )
    }

    @Test
    fun `getUsers should return users when API call is successful`() = runTest {
        // given
        val mockUsers = UserFixtures.getUserList(5)
        whenever(picPayService.getUsers()).thenReturn(Response.success(mockUsers))

        // when
        val result = userRepository.getUsers()

        // then
        Assert.assertTrue(result.isSuccess)
        Assert.assertEquals(mockUsers, result.getOrNull())
    }

    @Test
    fun `getUsers should return right Exception when specific HttpException is thrown`() =
        runTest {
            // given
            val errorResponse = Response.error<List<User>>(
                404,
                "{}".toResponseBody("application/json".toMediaTypeOrNull())
            )
            whenever(picPayService.getUsers()).thenThrow(HttpException(errorResponse))

            // when
            val result = userRepository.getUsers()

            // then
            Assert.assertTrue(result.isFailure)
            Assert.assertTrue(result.exceptionOrNull() is ResourceNotFoundException)
        }

    @Test
    fun `getUsers should return failure with UnknowErrorException when a generic exception is thrown`() =
        runTest {
            // given
            whenever(picPayService.getUsers()).thenThrow(RuntimeException())

            // when
            val result = userRepository.getUsers()

            // then
            Assert.assertTrue(result.isFailure)
            Assert.assertTrue(result.exceptionOrNull() is UnknowErrorException)
            Assert.assertEquals("Ocorreu um erro desconhecido.", result.exceptionOrNull()?.message)
        }

}