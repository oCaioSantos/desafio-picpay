package com.picpay.desafio.android.di

import androidx.lifecycle.SavedStateHandle
import com.picpay.desafio.android.presentation.viewmodel.MainViewModel
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.KoinTest
import org.koin.test.verify.definition
import org.koin.test.verify.injectedParameters
import org.koin.test.verify.verify

@OptIn(KoinExperimentalAPI::class)
class CheckModulesTest : KoinTest {

    @Test
    fun checkAllModules() {
        appModule.verify(
            injections = injectedParameters(
                definition<MainViewModel>(SavedStateHandle::class)
            )
        )
    }

}