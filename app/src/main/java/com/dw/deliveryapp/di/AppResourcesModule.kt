package com.dw.deliveryapp.di

import android.app.Application
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppResourcesModule {

    @Provides
    @Singleton
    fun provideAppResources(application: Application): Resources {
        return application.resources
    }
}