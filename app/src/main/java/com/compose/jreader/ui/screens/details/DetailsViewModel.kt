package com.compose.jreader.ui.screens.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.jreader.data.model.BookUi
import com.compose.jreader.data.model.UiState
import com.compose.jreader.data.repository.BookRepository
import com.compose.jreader.utils.getUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: BookRepository
) : ViewModel() {

    val bookInfo = mutableStateOf(UiState<BookUi>())

    val errorMsg = mutableStateOf("")

    /**
     * To get a book info from API
     * @param bookId Id of the book to be fetched
     */
    fun getBookInfo(bookId: String) {
        bookInfo.value = UiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            bookInfo.value = repository.getBookInfo(bookId).getUiState()
        }
    }

    /**
     * To save [BookUi] into fireStore
     * @param bookData [BookUi] model of the book to be saved
     * @param onSuccess Callback function to notify on save success
     */
    fun saveBook(bookData: BookUi?, onSuccess: () -> Unit) {
        viewModelScope.launch {
            repository.saveBookToFirebase(bookData) { isSuccess, msg ->
                if (isSuccess) {
                    onSuccess()
                    errorMsg.value = ""
                } else errorMsg.value = msg.toString()
            }
        }
    }

}