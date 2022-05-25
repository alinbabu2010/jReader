package com.compose.jreader.di

import com.compose.jreader.network.ApiLoader
import com.compose.jreader.network.ApiLoaderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ApiModule {

    @Binds
    abstract fun bindApiLoader(
        apiLoaderImpl: ApiLoaderImpl
    ): ApiLoader

}