package com.blockchain.app.di.module

import com.blockchain.app.presentation.ChartActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class TransactionActivityModule {
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeAssistActionActivity() : ChartActivity

}