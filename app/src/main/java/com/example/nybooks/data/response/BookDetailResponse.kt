package com.example.nybooks.data.response

import com.example.nybooks.data.model.Book


data class BookDetailResponse(

    val author: String,
    val description: String,
    val title: String
) {
    fun getBookModel() = Book(
        title = this.title,
        author = this.author,
        description = this.description
    )
}