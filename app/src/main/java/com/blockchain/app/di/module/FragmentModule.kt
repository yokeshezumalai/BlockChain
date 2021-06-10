package com.blockchain.app.di.module

import com.blockchain.app.presentation.FilterDialogFragment
import com.blockchain.app.presentation.ChartFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeTransactionFragment(): ChartFragment

    @ContributesAndroidInjector
    abstract fun contributeFilterDialogFragment(): FilterDialogFragment
}