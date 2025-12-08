package com.outivox.myabc.di

import com.outivox.myabc.core.navigation.NavigationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { NavigationViewModel() }
}
