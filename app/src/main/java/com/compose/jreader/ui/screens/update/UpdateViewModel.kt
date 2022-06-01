package com.compose.jreader.ui.screens.update

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.jreader.data.model.BookUi
import com.compose.jreader.data.model.BookUpdateValue
import com.compose.jreader.data.repository.FireRepository
import com.compose.jreader.ui.model.UiState
import com.compose.jreader.utils.UpdateManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel @Inject constructor(
    private val repository: FireRepository,
    private val updateManager: UpdateManager
) : ViewModel() {

    private lateinit var data: BookUi

    suspend fun getBook(bookId: String): UiState<BookUi> {
        val bookData = repository.getBookById(bookId)
        return if (bookData != null) {
            data = bookData
            UiState(data = data)
        } else UiState()
    }

    fun updateBook(
        bookUpdateValue: BookUpdateValue,
        onUpdate: (Boolean) -> Unit
    ) {
        val isValid = updateManager.isUpdateValid(bookUpdateValue, data)
        if (!isValid) {
            onUpdate(false)
            return
        } else {
            viewModelScope.launch {
                repository.updateBook(
                    data.id,
                    updateManager.bookData
                ).collectLatest {
                    onUpdate(it)
                }
            }
        }
    }


}