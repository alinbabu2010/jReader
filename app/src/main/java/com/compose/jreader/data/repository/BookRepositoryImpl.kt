package com.compose.jreader.data.repository

import com.compose.jreader.data.firebase.DatabaseSource
import com.compose.jreader.data.model.Book
import com.compose.jreader.data.model.BookUi
import com.compose.jreader.data.model.Resource
import com.compose.jreader.data.network.ApiLoader
import com.compose.jreader.data.wrappers.ResponseWrapper
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepositoryImpl @Inject constructor(
    private val apiLoader: ApiLoader,
    private val databaseSource: DatabaseSource
) : BookRepository {

    /**
     * To get list of books info from API
     * @param searchQuery Search keyword to be searched
     * @return An instance [Resource]
     */
    override fun getBooks(searchQuery: String): Resource<List<Book>> {
        return when (val response = apiLoader.getAllBooks(searchQuery)) {
            is ResponseWrapper.Error -> Resource.error(response.exception)
            is ResponseWrapper.Success -> {
                if (response.data.isNullOrEmpty()) Resource.empty()
                else Resource.success(response.data)
            }
        }
    }

    /**
     * To get a book info from API
     * @param bookId Id of the book to be fetched
     * @return An instance [Resource]
     */
    override fun getBookInfo(bookId: String): Resource<Book> {
        return when (val response = apiLoader.getBookInfo(bookId)) {
            is ResponseWrapper.Error -> Resource.error(response.exception)
            is ResponseWrapper.Success -> {
                if (response.data == null) Resource.empty()
                else Resource.success(response.data)
            }
        }
    }

    /**
     * To save [BookUi] into database
     * @param bookData [BookUi] model of the book to be saved
     * @return Flow emitting a [String] value - On success emits a empty string else emits the error message
     */
    override fun saveBookToFirebase(bookData: BookUi?) = channelFlow {
        databaseSource.saveBookToFirebase(bookData).collectLatest { result ->
            send(result)
        }
    }


}