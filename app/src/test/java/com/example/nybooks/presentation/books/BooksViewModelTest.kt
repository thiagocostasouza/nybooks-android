package com.example.nybooks.presentation.books

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.nybooks.data.BooksResult
import com.example.nybooks.data.model.Book
import com.example.nybooks.data.repository.BooksRepository
import com.nhaarman.mockitokotlin2.verify
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BooksViewModelTest {
    @get:Rule
    val rule:TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var booksLiveDataObserver: Observer<List<Book>>

    @Mock
    private lateinit var viewFlipperLiveDataObserver: Observer<Pair<Int, Int?>>

    private lateinit var viewModel: BooksViewModel

    @Test
    fun `when view model getBook get Success then sets booksLiveData`(){
        //Arrange
        val books = listOf(
            Book("title 1" , "author 1" , "description 1")
        )

        val resultSuccess = MockRepository(BooksResult.Success(books))
        viewModel = BooksViewModel(resultSuccess)
        viewModel.bookEvent.observeForever(booksLiveDataObserver)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        //Act
        viewModel.getBooks()

        //Assert
        verify(booksLiveDataObserver).onChanged(books)
        verify(viewFlipperLiveDataObserver).onChanged(Pair(1, null))
    }
}
class MockRepository(private val result: BooksResult): BooksRepository {
    override fun getBooks(booksResultCallback: (result: BooksResult) -> Unit) {
        booksResultCallback(result)
    }
}