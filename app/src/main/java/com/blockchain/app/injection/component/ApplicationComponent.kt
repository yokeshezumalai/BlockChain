package com.blockchain.app.injection.component
import com.blockchain.app.application.BaseApplication
import com.blockchain.app.injection.module.ActivityBuilderModule
import com.blockchain.app.injection.module.ApplicationModule
import com.blockchain.app.injection.module.ContextModule
import com.blockchain.app.injection.module.FragmentBuilderModule
import com.blockchain.app.injection.module.NetworkModule
import com.blockchain.app.injection.module.ViewModelFactoryModule
import com.blockchain.app.injection.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton



@ApplicationScope
@Singleton
@Component(modules = [
    NetworkModule::class,
    ActivityBuilderModule::class,
    FragmentBuilderModule::class,
    ContextModule::class,
    ViewModelFactoryModule::class,
    ViewModelModule::class,
    AndroidSupportInjectionModule::class,
    ApplicationModule::class
])
interface ApplicationComponent : AndroidInjector<BaseApplication> {

    override fun inject(application: BaseApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: BaseApplication): Builder
        fun build(): ApplicationComponent
    }
}