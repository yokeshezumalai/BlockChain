package com.blockchain.app.di.module

import com.blockchain.app.presentation.TransactionFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeTransactionFragment(): TransactionFragment
}