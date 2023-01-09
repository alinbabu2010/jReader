package com.compose.jreader.di

import com.compose.jreader.data.firebase.DatabaseSource
import com.compose.jreader.data.firebase.DatabaseSourceImpl
import com.compose.jreader.data.model.Book
import com.compose.jreader.data.model.BookUi
import com.compose.jreader.data.network.ApiLoader
import com.compose.jreader.data.network.ApiLoaderImpl
import com.compose.jreader.data.repository.*
import com.compose.jreader.utils.BookToBookUiMapper
import com.compose.jreader.utils.Mapper
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
    abstract fun bindBookUiMapper(
        bookUiMapper: BookToBookUiMapper
    ): Mapper<Book, BookUi>

    @Binds
    abstract fun bindBookRepository(
        bookRepositoryImpl: BookRepositoryImpl
    ): BookRepository

    @Binds
    abstract fun bindFireStoreRepository(
        fireStoreRepositoryImpl: FireStoreRepositoryImpl
    ): FireStoreRepository

    @Binds
    abstract fun bindLoginRepository(
        loginRepositoryImpl: LoginRepositoryImpl
    ): LoginRepository

    @Binds
    abstract fun bindDatabaseSource(
        databaseSourceImpl: DatabaseSourceImpl
    ): DatabaseSource

}