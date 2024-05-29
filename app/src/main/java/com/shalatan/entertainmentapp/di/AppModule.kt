package com.shalatan.entertainmentapp.di

import com.shalatan.entertainmentapp.network.ApiHelper
import com.shalatan.entertainmentapp.network.ApiHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideAPIHelper(apiHelperImpl: ApiHelperImpl): ApiHelper {
        return apiHelperImpl
    }
}