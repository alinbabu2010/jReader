package com.compose.jreader.ui.screens.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.jreader.data.model.BookUi
import com.compose.jreader.data.repository.BookRepository
import com.compose.jreader.ui.model.UiState
import com.compose.jreader.utils.toBookUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: BookRepository,
) : ViewModel() {

    val bookInfo = mutableStateOf(UiState<BookUi>())

    private val _savedState = MutableSharedFlow<String?>()
    val savedState = _savedState.asSharedFlow()

    /**
     * To get a book info from API
     * @param bookId Id of the book to be fetched
     */
    fun getBookInfo(bookId: String) {
        bookInfo.value = UiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getBookInfo(bookId)
            bookInfo.value = response.toBookUiState()
        }
    }

    /**
     * To save [BookUi] into fireStore
     * @param bookData [BookUi] model of the book to be saved
     */
    fun saveBook(bookData: BookUi?) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveBookToFirebase(bookData).collect {
                _savedState.emit(it)
            }
        }
    }

}