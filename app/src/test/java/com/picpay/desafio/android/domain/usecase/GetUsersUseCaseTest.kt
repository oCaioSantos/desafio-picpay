package com.picpay.desafio.android.domain.usecase

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.domain.exception.UnknowErrorException
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.fixtures.UserFixtures
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetUsersUseCaseTest {

    private lateinit var getUsersUseCase: GetUsersUseCase

    @Mock
    private lateinit var userRepository: UserRepository

    @Before
    fun setup() {
        getUsersUseCase = GetUsersUseCase(userRepository)
    }

    @Test
    fun `should return success when repository succeed`() = runTest {
        // given
        val mockUsers = UserFixtures.getUserList(5)
        whenever(userRepository.getUsers()).thenReturn(Result.success(mockUsers))

        // when
        val result = getUsersUseCase()

        // then
        verify(userRepository).getUsers()
        Assert.assertEquals(Result.success(mockUsers), result)
    }

    @Test
    fun `should return failure when repository fails`() = runTest {
        // given
        val exception = UnknowErrorException()
        whenever(userRepository.getUsers()).thenReturn(Result.failure(exception))

        // when
        val result = getUsersUseCase()

        // then
        verify(userRepository).getUsers()
        Assert.assertEquals(Result.failure<List<User>?>(exception), result)
    }
}