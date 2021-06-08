package com.blockchain.app.di.component

import com.blockchain.app.di.module.NetworkModule
import com.blockchain.app.di.module.ViewModelModule
import dagger.Module


@Module(includes = [ViewModelModule::class, NetworkModule::class])
class AppModule {


}