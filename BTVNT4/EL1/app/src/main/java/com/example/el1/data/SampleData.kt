package com.example.el1.data

object SampleData {
    // Danh sách sách
    val book1 = Book("s01", "Sách 01")
    val book2 = Book("s02", "Sách 02")

    // Danh sách sinh viên
    val students = listOf(
        Student("svA", "Nguyễn Văn A", listOf(book1, book2)),
        Student("svB", "Nguyễn Thị B", listOf(book1)),
        Student("svC", "Nguyễn Văn C", emptyList()) // Nguyễn Văn C không mượn sách
    )
}