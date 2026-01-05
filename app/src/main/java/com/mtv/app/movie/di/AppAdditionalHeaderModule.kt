package com.mtv.app.movie.di

import com.mtv.app.movie.config.AppAdditionalHeaderProvider
import com.mtv.based.core.network.header.AdditionalHeaderProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppAdditionalHeaderModule {

    @Binds
    abstract fun bindAppAdditionalHeaderProvider(
        impl: AppAdditionalHeaderProvider
    ): AdditionalHeaderProvider
}
