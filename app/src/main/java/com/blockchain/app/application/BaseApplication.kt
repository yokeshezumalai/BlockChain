package com.blockchain.app.application

import com.blockchain.app.injection.component.DaggerApplicationComponent
import dagger.android.support.DaggerApplication


class BaseApplication : DaggerApplication() {
    private val applicationInjector = DaggerApplicationComponent.builder()
        .application(this)
        .build()

    override fun applicationInjector() = applicationInjector
}