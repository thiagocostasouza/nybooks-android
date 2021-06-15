package com.example.nybooks.presentation.books

import android.os.Bundle
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nybooks.R
import com.example.nybooks.data.repository.BookApiDataSource
import com.example.nybooks.databinding.ActivityBooksBinding
import com.example.nybooks.presentation.base.BaseActivity
import com.example.nybooks.presentation.details.BookDetailsActivity

class BooksActivity : BaseActivity() {
    private val toolbar by lazy { findViewById<Toolbar>(R.id.include_toolbar) }
    private val rvBooks by lazy { findViewById<RecyclerView>(R.id.rvBooks) }
    private val vFlipper by lazy { findViewById<ViewFlipper>(R.id.viewFlipperBooks) }
    private val tvError by lazy { findViewById<TextView>(R.id.tvError) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        setupToolbar(toolbar, R.string.books)

        val viewModel: BooksViewModel =
            BooksViewModel.ViewModelFactory(BookApiDataSource()).create(BooksViewModel::class.java)
        viewModel.bookEvent.observe(this, {
            it?.let { books ->
                with(rvBooks) {
                    layoutManager =
                        LinearLayoutManager(this@BooksActivity, RecyclerView.VERTICAL, false)
                    setHasFixedSize(true)
                    adapter = BooksAdapter(books) { book ->
                        val intent = BookDetailsActivity.getStartIntent(
                            this@BooksActivity,
                            book.title,
                            book.description
                        )
                        this@BooksActivity.startActivity(intent)

                    }
                }
            }
        })

        viewModel.viewFlipperLiveData.observe(this) {
            it?.let { viewFlipper ->
                vFlipper.displayedChild = viewFlipper.first
                viewFlipper.second?.let { errorMessageResId ->
                    tvError.text = getString(errorMessageResId)
                }
            }
        }
        viewModel.getBooks()
    }
}
