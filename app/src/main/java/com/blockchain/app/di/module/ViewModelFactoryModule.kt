package com.blockchain.app.di.module

import androidx.lifecycle.ViewModelProvider
import com.blockchain.base.presentation.BaseViewModelFactory
import dagger.Binds
import dagger.Module


@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: BaseViewModelFactory): ViewModelProvider.Factory
}
