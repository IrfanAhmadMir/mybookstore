package com.example.mybookstore

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookList")
data class Book(
    @PrimaryKey(autoGenerate = true)
    var bookId: Int = 0,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "author") var author: String
)