package com.blockchain.app.di.module

import androidx.lifecycle.ViewModel
import com.blockchain.app.di.util.ViewModelKey
import com.blockchain.app.presentation.ChartViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ChartViewModel::class)
    abstract fun bindTransactionViewModel(chartViewModel: ChartViewModel): ViewModel
}
