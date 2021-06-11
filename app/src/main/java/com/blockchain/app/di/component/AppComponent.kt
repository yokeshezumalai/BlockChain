package com.blockchain.app.di.component

import android.app.Application
import com.blockchain.app.application.BaseApplication
import com.blockchain.app.di.module.TransactionActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@ApplicationScope
@Singleton
@Component(
        modules = [
            AndroidInjectionModule::class,
            AppModule::class,
            TransactionActivityModule::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: BaseApplication)

}