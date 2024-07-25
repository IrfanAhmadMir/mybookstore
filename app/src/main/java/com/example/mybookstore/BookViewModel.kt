package com.example.mybookstore

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class BookViewModel(private val bookRepository: BookRepository) : ViewModel() {

    val books: LiveData<List<Book>> = bookRepository.allBooks

    private val _book = MutableLiveData<Book?>()
    val book: LiveData<Book?> get() = _book

    fun loadBook(bookId: Int) = viewModelScope.launch {
        _book.value = bookRepository.getBook(bookId)
    }

    fun addBook(book: Book) = viewModelScope.launch {
        bookRepository.addBook(book)
    }

    fun updateBook(book: Book) = viewModelScope.launch {
        bookRepository.updateBook(book)
    }

    fun deleteBook(book: Book) = viewModelScope.launch {
        bookRepository.deleteBook(book)
    }
}
