package com.picpay.desafio.android.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.domain.exception.ForbbidenException
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.usecase.GetUsersUseCase
import com.picpay.desafio.android.fixtures.UserFixtures
import com.picpay.desafio.android.presentation.stateholder.MainStateHolder
import com.picpay.desafio.android.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var getUsersUseCase: GetUsersUseCase

    private lateinit var viewModel: MainViewModel

    @Mock
    private lateinit var observer: Observer<MainStateHolder>

    private val savedStateInstance = SavedStateHandle()

    @Before
    fun setup() {
        viewModel = MainViewModel(
            savedStateHandle = savedStateInstance,
            getUsersUseCase = getUsersUseCase
        )
        viewModel.uiState.observeForever(observer)
    }

    @After
    fun tearDown() {
        viewModel.uiState.removeObserver(observer)
        savedStateInstance.remove<List<User>>(MainViewModel.KEY)
        savedStateInstance.clearSavedStateProvider(MainViewModel.KEY)
    }

    @Test
    fun `loadUsers should set Success state when usecase returns data`() = runTest {
        // given
        val expectedData = emptyList<User>()
        whenever(getUsersUseCase()).thenReturn(Result.success(expectedData))

        // when
        viewModel.loadUsers()
        advanceUntilIdle()

        // then
        Assert.assertTrue(viewModel.uiState.value is MainStateHolder.Success)
    }

    @Test
    fun `loadUsers should set savedInstance data after first request`() = runTest {
        // given
        val expectedData = UserFixtures.getUserList(5)
        whenever(getUsersUseCase()).thenReturn(Result.success(expectedData))

        // when
        viewModel.loadUsers()
        advanceUntilIdle()

        // then
        val cachedUsers = viewModel.savedStateHandle.get<List<User>>(MainViewModel.KEY)
        Assert.assertNotNull(cachedUsers)
    }

    @Test
    fun `loadUsers should set Error state when usecase returns Exception`() = runTest {
        // given
        val expectedData = ForbbidenException()
        whenever(getUsersUseCase()).thenReturn(Result.failure(expectedData))

        // when
        viewModel.loadUsers()
        advanceUntilIdle()

        // then
        Assert.assertTrue(viewModel.uiState.value is MainStateHolder.Error)
        Assert.assertTrue((viewModel.uiState.value as MainStateHolder.Error).message == expectedData.message)
    }

}