package com.mtv.app.movie.feature.di

import com.mtv.app.movie.data.model.response.LoginResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseMapperModule {

    @Provides
    fun provideLoginResponseClass(): Class<LoginResponse> = LoginResponse::class.java
}
