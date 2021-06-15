package com.example.nybooks.presentation.books

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nybooks.R
import com.example.nybooks.data.model.Book

class BooksAdapter(
    private val listBook: List<Book>,
    val onItemClickListener: ((book: Book) -> Unit)
) : RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)

        return BooksViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        holder.bindView(listBook[position])
    }

    override fun getItemCount() = listBook.size

    class BooksViewHolder(
        itemView: View,
        private val onItemClickListener: ((book: Book) -> Unit)
    ) : RecyclerView.ViewHolder(itemView) {

        private val textTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val textAuthor: TextView = itemView.findViewById(R.id.tvAuthor)

        fun bindView(book: Book) {
            textTitle.text = book.title
            textAuthor.text = book.author

            itemView.setOnClickListener {
            onItemClickListener.invoke(book)
            }
        }
    }
}