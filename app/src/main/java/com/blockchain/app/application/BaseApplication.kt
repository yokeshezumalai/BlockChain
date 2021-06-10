package com.blockchain.app.application

import android.app.Application
import androidx.lifecycle.LifecycleObserver
import com.blockchain.app.di.AppInjector
import com.blockchain.app.di.component.AppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


open class BaseApplication: Application(), LifecycleObserver, HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector() = dispatchingAndroidInjector


    override fun onCreate() {
        super.onCreate()
        initializeDagger()

    }

    private fun initializeDagger() {
        appComponent = AppInjector.init(this)
    }

    companion object {
        lateinit var appComponent: AppComponent
    }

}