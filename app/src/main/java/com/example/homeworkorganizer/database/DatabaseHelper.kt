package com.example.homeworkorganizer.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.homeworkorganizer.model.Homework
import com.example.homeworkorganizer.model.Reminder
import com.example.homeworkorganizer.model.Task
import com.example.homeworkorganizer.model.TeacherNote

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "homeworkorganizer.db"
        private const val DATABASE_VERSION = 1

        private const val CREATE_TABLE_HOMEWORK = """
            CREATE TABLE Homework (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT,
                description TEXT,
                dueDate TEXT,
                subject TEXT
            )
        """

        private const val CREATE_TABLE_REMINDER = """
            CREATE TABLE Reminder (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                homeworkId INTEGER,
                reminderDate TEXT,
                subject TEXT,
                description TEXT,
                FOREIGN KEY(homeworkId) REFERENCES Homework(id)
            )
        """

        private const val CREATE_TABLE_TASK = """
            CREATE TABLE Task (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                homeworkId INTEGER,
                status TEXT,
                FOREIGN KEY(homeworkId) REFERENCES Homework(id)
            )
        """

        private const val CREATE_TABLE_TEACHER_NOTE = """
            CREATE TABLE TeacherNote (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                homeworkId INTEGER,
                note TEXT,
                FOREIGN KEY(homeworkId) REFERENCES Homework(id)
            )
        """
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_HOMEWORK)
        db.execSQL(CREATE_TABLE_REMINDER)
        db.execSQL(CREATE_TABLE_TASK)
        db.execSQL(CREATE_TABLE_TEACHER_NOTE)

        // Demo dati latviešu valodā
        val demoHomework1 = Homework(1, "Matemātikas mājasdarbs", "Izpildīt uzdevumus no 1 līdz 20", "2024-06-01", "Matemātika")
        val demoHomework2 = Homework(2, "Vēstures eseja", "Uzrakstīt eseju par Otro pasaules karu", "2024-06-05", "Vēsture")
        val demoHomework3 = Homework(3, "Zinātnes projekts", "Izveidot vulkāna modeli", "2024-06-10", "Zinātne")

        val demoReminder1 = Reminder(1, 1, "2024-05-31 08:00", demoHomework1.subject, demoHomework1.description)
        val demoReminder2 = Reminder(2, 2, "2024-06-03 10:00", demoHomework2.subject, demoHomework2.description)
        val demoReminder3 = Reminder(3, 3, "2024-06-08 12:00", demoHomework3.subject, demoHomework3.description)

        val demoTask1 = Task(1, 1, "sākts", demoHomework1.title, demoHomework1.description, demoHomework1.dueDate, demoHomework1.subject)
        val demoTask2 = Task(2, 2, "pabeigts", demoHomework2.title, demoHomework2.description, demoHomework2.dueDate, demoHomework2.subject)
        val demoTask3 = Task(3, 3, "atlikts", demoHomework3.title, demoHomework3.description, demoHomework3.dueDate, demoHomework3.subject)

        val demoTeacherNote1 = TeacherNote(1, 1, "Neaizmirstiet parādīt savu darbu")
        val demoTeacherNote2 = TeacherNote(2, 2, "Pārbaudiet vēsturiskos faktus uzmanīgi")
        val demoTeacherNote3 = TeacherNote(3, 3, "Iekļaujiet detalizētu aprakstu")

        insertDemoData(db, demoHomework1, demoReminder1, demoTask1, demoTeacherNote1)
        insertDemoData(db, demoHomework2, demoReminder2, demoTask2, demoTeacherNote2)
        insertDemoData(db, demoHomework3, demoReminder3, demoTask3, demoTeacherNote3)
    }

    private fun insertDemoData(db: SQLiteDatabase, homework: Homework, reminder: Reminder, task: Task, teacherNote: TeacherNote) {
        val insertHomework = "INSERT INTO Homework (id, title, description, dueDate, subject) VALUES (${homework.id}, '${homework.title}', '${homework.description}', '${homework.dueDate}', '${homework.subject}')"
        val insertReminder = "INSERT INTO Reminder (id, homeworkId, reminderDate, subject, description) VALUES (${reminder.id}, ${reminder.homeworkId}, '${reminder.reminderDate}', '${reminder.subject}', '${reminder.description}')"
        val insertTask = "INSERT INTO Task (id, homeworkId, status) VALUES (${task.id}, ${task.homeworkId}, '${task.status}')"
        val insertTeacherNote = "INSERT INTO TeacherNote (id, homeworkId, note) VALUES (${teacherNote.id}, ${teacherNote.homeworkId}, '${teacherNote.note}')"

        db.execSQL(insertHomework)
        db.execSQL(insertReminder)
        db.execSQL(insertTask)
        db.execSQL(insertTeacherNote)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Homework")
        db.execSQL("DROP TABLE IF EXISTS Reminder")
        db.execSQL("DROP TABLE IF EXISTS Task")
        db.execSQL("DROP TABLE IF EXISTS TeacherNote")
        onCreate(db)
    }
}
