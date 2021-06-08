package com.blockchain.app.injection.module
import com.blockchain.app.presentation.TransactionActivity
import com.blockchain.base.presentation.BaseActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector
    internal abstract fun bindBaseActivity(): BaseActivity

    @ContributesAndroidInjector
    internal abstract fun bindMainActivity(): TransactionActivity
}
