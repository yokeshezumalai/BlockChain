package com.blockchain.app.injection.module

import androidx.lifecycle.ViewModel
import com.blockchain.app.injection.util.ViewModelKey
import com.blockchain.app.presentation.TransactionViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(TransactionViewModel::class)
    internal abstract fun bindTransactionViewModel(transactionViewModel: TransactionViewModel): ViewModel
}
