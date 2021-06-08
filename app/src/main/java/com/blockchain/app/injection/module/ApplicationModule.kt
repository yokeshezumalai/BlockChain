package com.blockchain.app.injection.module
import android.app.Application
import com.blockchain.app.application.BaseApplication
import dagger.Binds
import javax.inject.Singleton
import dagger.Module

@Module
abstract class ApplicationModule {
    @Binds
    @Singleton
    abstract fun bindApplication(application: BaseApplication): Application
}
