package com.compose.jreader.data.repository

import com.compose.jreader.data.firebase.DatabaseSource
import com.compose.jreader.data.model.BookUi
import com.compose.jreader.data.model.Resource
import com.compose.jreader.data.wrappers.ResponseWrapper
import com.compose.jreader.data.network.ApiLoader
import com.compose.jreader.utils.Mapper
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val apiLoader: ApiLoader,
    private val mapper: Mapper,
    private val databaseSource: DatabaseSource
) {

    /**
     * To get list of books info from API
     * @param searchQuery Search keyword to be searched
     * @return An instance [Resource]
     */
    fun getBooks(searchQuery: String): Resource<List<BookUi>> {
        return when (val response = apiLoader.getAllBooks(searchQuery)) {
            is ResponseWrapper.Error -> Resource.error(response.exception)
            is ResponseWrapper.Success -> {
                if (response.data.isNullOrEmpty()) Resource.empty()
                else Resource.success(mapper.getBookUiList(response.data))
            }
        }
    }

    /**
     * To get a book info from API
     * @param bookId Id of the book to be fetched
     * @return An instance [Resource]
     */
    fun getBookInfo(bookId: String): Resource<BookUi> {
        return when (val response = apiLoader.getBookInfo(bookId)) {
            is ResponseWrapper.Error -> Resource.error(response.exception)
            is ResponseWrapper.Success -> {
                if (response.data == null) Resource.empty()
                else Resource.success(mapper.getBookUi(response.data))
            }
        }
    }

    /**
     * To save [BookUi] into database
     * @param bookData [BookUi] model of the book to be saved
     * @return Flow emitting a [String] value - On success emits a empty string else emits the error message
     */
    fun saveBookToFirebase(bookData: BookUi?) = channelFlow {
        databaseSource.saveBookToFirebase(bookData).collectLatest { result ->
            send(
                if (result.isNullOrBlank()) null
                else result.toString()
            )
        }
    }


}