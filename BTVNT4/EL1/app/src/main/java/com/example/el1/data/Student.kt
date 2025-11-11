package com.example.el1.data

data class Student(
    val id: String,
    val name: String,
    val borrowedBooks: List<Book>
)