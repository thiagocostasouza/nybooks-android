package com.example.nybooks.presentation.books

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nybooks.R
import com.example.nybooks.data.BooksResult
import com.example.nybooks.data.model.Book
import com.example.nybooks.data.repository.BooksRepository
import java.lang.IllegalArgumentException

class BooksViewModel(val dataSource: BooksRepository) : ViewModel() {

    private val _booksEvent = MutableLiveData<List<Book>>()
    val bookEvent: LiveData<List<Book>>
        get() = _booksEvent

   private val _viewFlipperLiveData: MutableLiveData<Pair<Int, Int?>> = MutableLiveData()
    val viewFlipperLiveData: LiveData<Pair<Int, Int?>>
        get() = _viewFlipperLiveData

    fun getBooks() {
        dataSource.getBooks { result: BooksResult ->
            when (result) {
                is BooksResult.Success -> {
                    _booksEvent.value = result.books
                    _viewFlipperLiveData.value = Pair(BooksViewModel.VIEW_FLIPPER_BOOKS, null)
                }
                is BooksResult.ApiError -> {
                    if (result.statusCode == 401) {
                        _viewFlipperLiveData.value =
                            Pair(BooksViewModel.VIEW_FLIPPER_ERROR, R.string.books_error_401)
                    } else {
                        Pair(BooksViewModel.VIEW_FLIPPER_ERROR, R.string.books_error_400_generic)
                    }
                }
                is BooksResult.ServerError -> {
                    Pair(BooksViewModel.VIEW_FLIPPER_ERROR, R.string.books_error_500_generic)
                }
            }
        }
    }

    class ViewModelFactory(val dataSource: BooksRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BooksViewModel::class.java)) {
                return BooksViewModel(dataSource) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }

    companion object {
        private const val VIEW_FLIPPER_BOOKS = 1
        private const val VIEW_FLIPPER_ERROR = 2

    }

}

