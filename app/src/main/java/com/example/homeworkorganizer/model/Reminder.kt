package com.example.homeworkorganizer.model

data class Reminder(
    val id: Int,
    val homeworkId: Int,
    val reminderDate: String, // formātā "yyyy-MM-dd HH:mm"
    val subject: String, // No Homework modeļa
    val description: String // No Homework modeļa
)
