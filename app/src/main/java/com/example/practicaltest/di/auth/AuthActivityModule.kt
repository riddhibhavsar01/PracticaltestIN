package com.example.practicaltest.di.auth

import com.example.practicaltest.di.app.scope.ActivityScoped
import com.example.practicaltest.ui.auth.AuthActivity
import com.example.practicaltest.ui.auth.adapter.AuthPagerAdapter
import com.example.practicaltest.utils.BaseResourceProvider
import com.example.practicaltest.utils.ResourceProvider
import dagger.Module
import dagger.Provides


@Module
class AuthActivityModule {
    @Provides
    @ActivityScoped
    fun provideResourceProvider(context: AuthActivity): BaseResourceProvider {
        return ResourceProvider(context)
    }

    @Provides
    @ActivityScoped
    fun provideAuthPagerAdapter(activity: AuthActivity): AuthPagerAdapter {
        return AuthPagerAdapter(activity)
    }
}