package com.example.homeworkorganizer.model

data class Homework(
    val id: Int,
    val title: String,
    val description: String,
    val dueDate: String, // formātā "yyyy-MM-dd"
    val subject: String
)
