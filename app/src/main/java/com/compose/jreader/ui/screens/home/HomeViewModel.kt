package com.compose.jreader.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.jreader.data.model.BookUi
import com.compose.jreader.data.repository.FireRepository
import com.compose.jreader.ui.model.UiState
import com.compose.jreader.utils.getUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: FireRepository
) : ViewModel() {

    private val _listOfBooks = MutableStateFlow<UiState<List<BookUi>>>(UiState())
    val listOfBooks = _listOfBooks.asStateFlow()

    init {
        getAllBooks()
    }

    private fun getAllBooks() {
        _listOfBooks.value = UiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val uiState = repository.getAllBooks().getUiState()
            _listOfBooks.value = uiState
            Log.d("TAG", "getAllBooks: ${listOfBooks.value}")
        }
    }

}