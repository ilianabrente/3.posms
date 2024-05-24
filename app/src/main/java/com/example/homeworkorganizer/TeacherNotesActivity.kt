package com.example.homeworkorganizer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkorganizer.database.DatabaseManager

class TeacherNotesActivity : AppCompatActivity() {

    private lateinit var databaseManager: DatabaseManager
    private lateinit var recyclerViewTeacherNotes: RecyclerView
    private lateinit var teacherNoteAdapter: TeacherNoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_notes)
        setTitle("Skolotāju piezīmes")

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        databaseManager = DatabaseManager(this)
        recyclerViewTeacherNotes = findViewById(R.id.recyclerViewTeacherNotes)

        // Iegūstiet un parādiet demo skolotāju piezīmes
        val teacherNoteList = databaseManager.getAllTeacherNotes()
        teacherNoteAdapter = TeacherNoteAdapter(teacherNoteList)
        recyclerViewTeacherNotes.layoutManager = LinearLayoutManager(this)
        recyclerViewTeacherNotes.adapter = teacherNoteAdapter
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        databaseManager.close()
    }
}
