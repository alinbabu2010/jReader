package com.compose.jreader.ui.screens.home

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

    private val _listOfBooks = MutableStateFlow<
            UiState<Pair<List<BookUi>, List<BookUi>>>>(UiState())
    val listOfBooks = _listOfBooks.asStateFlow()

    fun getAllBooks() {
        _listOfBooks.value = if (_listOfBooks.value.data == null)
            UiState(isLoading = true) else listOfBooks.value
        viewModelScope.launch(Dispatchers.IO) {
            val uiState = repository.getAllBooks().getUiState()
            _listOfBooks.value = uiState
        }
    }

}