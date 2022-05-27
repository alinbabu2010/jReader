package com.compose.jreader.di

import com.compose.jreader.network.ApiLoader
import com.compose.jreader.network.ApiLoaderImpl
import com.compose.jreader.utils.Mapper
import com.compose.jreader.utils.UiMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class InterfaceModule {

    @Binds
    abstract fun bindApiLoader(
        apiLoaderImpl: ApiLoaderImpl
    ): ApiLoader

    @Binds
    abstract fun bindMapper(
        uiMapper: UiMapper
    ) : Mapper

}