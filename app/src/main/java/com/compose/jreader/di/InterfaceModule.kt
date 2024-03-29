package com.compose.jreader.di

import com.compose.jreader.data.firebase.DatabaseSource
import com.compose.jreader.data.firebase.DefaultDatabaseSource
import com.compose.jreader.data.network.ApiLoader
import com.compose.jreader.data.network.DefaultApiLoader
import com.compose.jreader.data.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class InterfaceModule {

    @Binds
    abstract fun bindApiLoader(
        defaultApiLoader: DefaultApiLoader
    ): ApiLoader

    @Binds
    abstract fun bindBookRepository(
        defaultBookRepository: DefaultBookRepository
    ): BookRepository

    @Binds
    abstract fun bindFireStoreRepository(
        defaultFireStoreRepository: DefaultFireStoreRepository
    ): FireStoreRepository

    @Binds
    abstract fun bindLoginRepository(
        defaultLoginRepository: DefaultLoginRepository
    ): LoginRepository

    @Binds
    abstract fun bindDatabaseSource(
        defaultDatabaseSource: DefaultDatabaseSource
    ): DatabaseSource

}