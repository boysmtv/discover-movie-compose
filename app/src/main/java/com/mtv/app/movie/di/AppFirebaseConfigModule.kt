package com.mtv.app.movie.di

import com.mtv.app.movie.config.AppFirebaseConfigProvider
import com.mtv.based.core.network.config.FirebaseConfigProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppFirebaseConfigModule {

    @Provides
    fun provideFirebaseConfigProvider(): FirebaseConfigProvider = AppFirebaseConfigProvider()
}