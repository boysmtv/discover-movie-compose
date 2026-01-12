package com.mtv.app.movie.feature.di

import com.mtv.app.movie.data.model.response.CheckResponse
import com.mtv.app.movie.data.model.response.LoginResponse
import com.mtv.app.movie.data.model.response.LogoutResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseMapperModule {

    @Provides
    fun provideLoginResponseClass(): Class<LoginResponse> = LoginResponse::class.java

    @Provides
    fun provideCheckResponseClass(): Class<CheckResponse> = CheckResponse::class.java

    @Provides
    fun provideLogoutResponseClass(): Class<LogoutResponse> = LogoutResponse::class.java

}
