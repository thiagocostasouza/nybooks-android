package com.example.nybooks.presentation.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.example.nybooks.R
import com.example.nybooks.presentation.base.BaseActivity

class BookDetailsActivity : BaseActivity() {
    private val titleDetail by lazy { findViewById<TextView>(R.id.bookDetailTitle) }
    private val descriptionDetail by lazy { findViewById<TextView>(R.id.bookDetailDescription) }
    private val toolbar by lazy { findViewById<Toolbar>(R.id.toolbarDt) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)

        setupToolbar(toolbar,R.string.book_details_title, true)
        titleDetail.text = intent.getStringExtra(EXTRA_TITLE)
        descriptionDetail.text = intent.getStringExtra(EXTRA_DESCRIPTION)

    }

    companion object {
        private const val EXTRA_TITLE = "EXTRA_TITLE"
        private const val EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION"

        fun getStartIntent(context: Context, title: String, description: String): Intent {
            return Intent(context, BookDetailsActivity::class.java).apply {
                putExtra(EXTRA_TITLE, title)
                putExtra(EXTRA_DESCRIPTION, description)
            }
        }
    }
}