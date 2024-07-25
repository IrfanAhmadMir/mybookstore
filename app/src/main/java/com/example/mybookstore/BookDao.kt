package com.example.mybookstore

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BookDao {

    @Query("SELECT * FROM bookList")
    fun getAllBooks(): LiveData<List<Book>>

    @Query("SELECT * FROM bookList WHERE bookId = :bookId")
    suspend fun getBook(bookId: Int): Book?

    @Insert
    suspend fun addBook(book: Book): Long

    @Update
    suspend fun updateBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)
}