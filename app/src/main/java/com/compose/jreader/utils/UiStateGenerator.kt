package com.compose.jreader.utils

import com.compose.jreader.data.model.Book
import com.compose.jreader.data.model.BookUi
import com.compose.jreader.data.model.Resource
import com.compose.jreader.ui.model.UiState
import javax.inject.Inject

class UiStateGenerator @Inject constructor(
    private val bookUiMapper: BookToBookUiMapper
) {

    fun generate(resource: Resource<Book>): UiState<BookUi> {
        val state = resource.getUiState()
        return UiState(
            state.data?.let { bookUiMapper.map(it) },
            state.isLoading,
            state.isError,
            state.message
        )
    }

    @JvmName("generate_list")
    fun generate(resource: Resource<List<Book>>): UiState<List<BookUi>> {
        val state = resource.getUiState()
        return UiState(
            state.data?.map { bookUiMapper.map(it) },
            state.isLoading,
            state.isError,
            state.message
        )
    }

}