package com.compose.jreader.ui.screens.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.jreader.data.model.BookUi
import com.compose.jreader.data.model.UiState
import com.compose.jreader.data.repository.BookRepository
import com.compose.jreader.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: BookRepository
) : ViewModel() {

    val listOfBooks: MutableState<UiState<List<BookUi>>> = mutableStateOf(UiState(isLoading = true))

    init {
        searchBooks("android")
    }

    /**
     * To search book lists based on given search query from API
     * @param query Query to be searched
     * @return An instance [UiState]
     */
    fun searchBooks(query: String) {
        listOfBooks.value = UiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getBooks(query)
            listOfBooks.value = when (response.status) {
                Status.SUCCESS -> UiState(response.data)
                Status.ERROR -> UiState(isError = true, message = response.message)
                Status.LOADING -> UiState(isLoading = true)
                Status.EMPTY_RESPONSE -> UiState()
            }
        }
    }

}