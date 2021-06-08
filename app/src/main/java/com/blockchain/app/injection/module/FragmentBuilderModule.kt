package com.blockchain.app.injection.module
import com.blockchain.app.presentation.TransactionFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {
    @ContributesAndroidInjector
    internal abstract fun bindTransactionFragment(): TransactionFragment
}
