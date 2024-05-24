package com.example.homeworkorganizer.model

data class Task(
    val id: Int,
    val homeworkId: Int,
    val status: String, // "sākts", "pabeigts", "atlikts"
    val title: String,  // No Homework modeļa
    val description: String,  // No Homework modeļa
    val dueDate: String,  // No Homework modeļa
    val subject: String  // No Homework modeļa
)
