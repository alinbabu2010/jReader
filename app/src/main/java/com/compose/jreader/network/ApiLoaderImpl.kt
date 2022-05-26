package com.compose.jreader.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.compose.jreader.BuildConfig
import com.compose.jreader.R
import com.compose.jreader.data.model.Book
import com.compose.jreader.data.model.ErrorResponse
import com.compose.jreader.data.wrappers.ResponseWrapper
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.stream.MalformedJsonException
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiLoaderImpl @Inject constructor(
    private val booksApi: BooksApi,
    @ApplicationContext val context: Context
) : ApiLoader {

    override fun getAllBooks(searchQuery: String): ResponseWrapper<List<Book>> {
        return when (val response = booksApi.getAllBooks(searchQuery).callWithExceptionHandling()) {
            is ResponseWrapper.Error -> ResponseWrapper.Error(
                response.errorCode,
                response.exception
            )
            is ResponseWrapper.Success -> ResponseWrapper.Success(response.data?.booksList)
        }
    }

    override fun getBookInfo(bookId: String): ResponseWrapper<Book> =
        booksApi.getBookInfo(bookId).callWithExceptionHandling()


    /**
     * This method will execute the `Call<T>` and handle the exceptions
     * that can happen. It returns either `Success<T>` or `Failure<T>`.
     *
     * Consumers of the API calls can check the result using this style:
     *
     *      result = RentselApi.someApiCall()
     *
     *      when (result){
     *          is Success -> doSomething(result.contents)
     *          is Failure -> showError(result.throwable.message)
     *      }
     *
     */
    private fun <T : Any> Call<T>.callWithExceptionHandling(): ResponseWrapper<T> {

        val serverErrorMsg = context.getString(R.string.server_error_message)

        var responseCode = 101
        var responseMsg = serverErrorMsg


        if (!isNetworkAvailable()) {
            return ResponseWrapper.Error(
                100,
                Throwable(context.getString(R.string.offline_message))
            )
        }


        return try {

            val response = execute()

            if (response.isSuccessful && response.body() != null) {

                ResponseWrapper.Success(response.body() as T)

            } else {

                responseCode = response.code()
                responseMsg = response.message()

                /*
                Retrofit currently doesn't convert its `errorBody` to JSON. We need to do that
                manually. Ref: https://github.com/square/retrofit/issues/1321
                */
                val errorBody = response.errorBody()?.string()

                val errorResponse = runCatching {
                    Gson().fromJson(
                        errorBody,
                        ErrorResponse::class.java
                    )
                }.getOrNull()

                when {

                    errorResponse == null -> throw java.lang.Exception(
                        if (BuildConfig.DEBUG)
                            context.getString(R.string.error_exception_message, errorBody)
                        else serverErrorMsg
                    )

                    errorResponse.error.message.isNotEmpty() -> ResponseWrapper.Error(
                        responseCode,
                        Throwable(if (BuildConfig.DEBUG) errorResponse.error.message else serverErrorMsg)
                    )

                    else -> throw java.lang.Exception(
                        if (BuildConfig.DEBUG)
                            context.getString(R.string.exception_message, errorBody)
                        else serverErrorMsg
                    )

                }
            }

        } catch (e: Exception) {

            if (e is JsonSyntaxException || e is MalformedJsonException || e is IllegalStateException) {
                responseCode = 100
                responseMsg = serverErrorMsg
            }

            if (responseMsg.isEmpty()) {
                responseMsg = serverErrorMsg
            }

            ResponseWrapper.Error(
                responseCode,
                Throwable(
                    if (BuildConfig.DEBUG)
                        "$responseCode: $responseMsg\n\n[Error Details]\n${e.localizedMessage}"
                    else serverErrorMsg
                )
            )
        }

    }

    /**
     * To check if internet connection is available or not
     * @return True on internet availability else false
     */
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        return when {
            actNw.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    actNw.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}