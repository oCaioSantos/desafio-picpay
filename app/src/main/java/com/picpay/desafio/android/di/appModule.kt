package com.picpay.desafio.android.di

import androidx.lifecycle.SavedStateHandle
import com.picpay.desafio.android.common.network.Rest
import com.picpay.desafio.android.common.network.interceptors.CacheInterceptor
import com.picpay.desafio.android.common.network.interceptors.NetworkLoggingInterceptor
import com.picpay.desafio.android.common.utils.getCache
import com.picpay.desafio.android.data.repository.remote.UserRepositoryImpl
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.domain.service.PicPayService
import com.picpay.desafio.android.domain.usecase.GetUsersUseCase
import com.picpay.desafio.android.presentation.viewmodel.MainViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<OkHttpClient> {
        OkHttpClient
            .Builder()
            .cache(getCache(androidContext()))
            .addNetworkInterceptor(CacheInterceptor())
            .addInterceptor(NetworkLoggingInterceptor())
            .build()
    }

    single<PicPayService> {
        Rest.getInstance(
            client = get()
        ).create(PicPayService::class.java)
    }

    single<UserRepository> {
        UserRepositoryImpl(
            picPayService = get()
        )
    }

    factory<GetUsersUseCase> {
        GetUsersUseCase(
            usersRepository = get()
        )
    }

    viewModel<MainViewModel> { (savedStateHandle: SavedStateHandle) ->
        MainViewModel(
            savedStateHandle = savedStateHandle,
            getUsersUseCase = get()
        )
    }

}