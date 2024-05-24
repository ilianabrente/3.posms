package com.example.homeworkorganizer.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.homeworkorganizer.model.*

class DatabaseManager(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun getAllHomework(): List<Homework> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM Homework", null)
        val homeworkList = mutableListOf<Homework>()

        if (cursor.moveToFirst()) {
            do {
                val homework = Homework(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("title")),
                    cursor.getString(cursor.getColumnIndexOrThrow("description")),
                    cursor.getString(cursor.getColumnIndexOrThrow("dueDate")),
                    cursor.getString(cursor.getColumnIndexOrThrow("subject"))
                )
                homeworkList.add(homework)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return homeworkList
    }

    fun updateHomework(homework: Homework): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("title", homework.title)
            put("description", homework.description)
            put("dueDate", homework.dueDate)
            put("subject", homework.subject)
        }
        return db.update("Homework", values, "id = ?", arrayOf(homework.id.toString()))
    }

    fun deleteHomework(homeworkId: Int): Int {
        val db = dbHelper.writableDatabase
        return db.delete("Homework", "id = ?", arrayOf(homeworkId.toString()))
    }

    fun insertReminder(reminder: Reminder): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("homeworkId", reminder.homeworkId)
            put("reminderDate", reminder.reminderDate)
            put("subject", reminder.subject)
            put("description", reminder.description)
        }
        return db.insert("Reminder", null, values)
    }

    fun getAllReminders(): List<Reminder> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery(
            """
            SELECT Reminder.id, Reminder.homeworkId, Reminder.reminderDate, Homework.subject, Homework.description
            FROM Reminder
            JOIN Homework ON Reminder.homeworkId = Homework.id
            """, null)
        val reminderList = mutableListOf<Reminder>()

        if (cursor.moveToFirst()) {
            do {
                val reminder = Reminder(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("homeworkId")),
                    cursor.getString(cursor.getColumnIndexOrThrow("reminderDate")),
                    cursor.getString(cursor.getColumnIndexOrThrow("subject")),
                    cursor.getString(cursor.getColumnIndexOrThrow("description"))
                )
                reminderList.add(reminder)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return reminderList
    }

    fun getAllTeacherNotes(): List<TeacherNote> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM TeacherNote", null)
        val teacherNoteList = mutableListOf<TeacherNote>()

        if (cursor.moveToFirst()) {
            do {
                val teacherNote = TeacherNote(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("homeworkId")),
                    cursor.getString(cursor.getColumnIndexOrThrow("note"))
                )
                teacherNoteList.add(teacherNote)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return teacherNoteList
    }

    fun insertHomework(homework: Homework): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("title", homework.title)
            put("description", homework.description)
            put("dueDate", homework.dueDate)
            put("subject", homework.subject)
        }
        return db.insert("Homework", null, values)
    }

    fun getAllTasks(): List<Task> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery(
            """
            SELECT Task.id, Task.homeworkId, Task.status, Homework.title, Homework.description, Homework.dueDate, Homework.subject
            FROM Task
            JOIN Homework ON Task.homeworkId = Homework.id
            """, null)
        val taskList = mutableListOf<Task>()

        if (cursor.moveToFirst()) {
            do {
                val task = Task(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("homeworkId")),
                    cursor.getString(cursor.getColumnIndexOrThrow("status")),
                    cursor.getString(cursor.getColumnIndexOrThrow("title")),
                    cursor.getString(cursor.getColumnIndexOrThrow("description")),
                    cursor.getString(cursor.getColumnIndexOrThrow("dueDate")),
                    cursor.getString(cursor.getColumnIndexOrThrow("subject"))
                )
                taskList.add(task)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return taskList
    }

    fun updateTask(task: Task): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("homeworkId", task.homeworkId)
            put("status", task.status)
        }
        return db.update("Task", values, "id = ?", arrayOf(task.id.toString()))
    }

    fun close() {
        dbHelper.close()
    }
}
