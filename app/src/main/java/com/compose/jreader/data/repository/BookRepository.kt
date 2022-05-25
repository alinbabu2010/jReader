package com.compose.jreader.data.repository

import com.compose.jreader.data.model.BookUi
import com.compose.jreader.data.wrappers.Resource
import com.compose.jreader.data.wrappers.ResponseWrapper
import com.compose.jreader.network.ApiLoader
import com.compose.jreader.utils.UiMapper
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val apiLoader: ApiLoader,
    private val uiMapper: UiMapper

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
                else Resource.success(uiMapper.getBookUiList(response.data))
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
                else Resource.success(uiMapper.getBookUi(response.data))
            }
        }
    }

}