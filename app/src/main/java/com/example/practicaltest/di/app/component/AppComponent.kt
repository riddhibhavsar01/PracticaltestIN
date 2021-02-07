package com.example.practicaltest.di.app.component

import android.app.Application
import com.example.practicaltest.BaseApplication
import com.example.practicaltest.di.app.module.*
import com.example.practicaltest.di.app.scope.AppScoped
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@AppScoped
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBindingModule::class,
    AppModule::class,
    ViewModelFactoryModule::class,
    NetworkModule::class,
    RoomDatabaseModule::class,
  PlaceModule::class
])
interface AppComponent : AndroidInjector<BaseApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}