package com.example.practicaltest.di.auth

import com.example.practicaltest.di.app.scope.FragmentScoped
import com.example.practicaltest.ui.auth.fragments.OtpVerificationFragment
import com.example.practicaltest.ui.auth.fragments.PhoneVerificationFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {
    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindOtpVerificationFragment(): OtpVerificationFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun bindPhoneVerificationFragment(): PhoneVerificationFragment

}