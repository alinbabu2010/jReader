package com.compose.jreader.data.network

import com.compose.jreader.data.model.Book
import com.compose.jreader.data.model.BookList
import com.compose.jreader.utils.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface BooksApi {

    @GET(Constants.ENDPOINT_VOLUME)
    fun getAllBooks(
        @Query(Constants.PARAM_QUERY) query: String
    ): Call<BookList>

    @GET(Constants.ENDPOINT_BOOK_INFO)
    fun getBookInfo(
        @Path(Constants.PARAM_BOOKID) bookId: String
    ): Call<Book>

}