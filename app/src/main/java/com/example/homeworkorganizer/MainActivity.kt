package com.example.homeworkorganizer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkorganizer.adapters.HomeworkAdapter
import com.example.homeworkorganizer.database.DatabaseManager

class MainActivity : AppCompatActivity() {

    private lateinit var databaseManager: DatabaseManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var homeworkAdapter: HomeworkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("Mājasdarbu organizētājs")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Reminder Channel"
            val descriptionText = "Channel for Homework Reminders"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("reminder_channel", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }



        databaseManager = DatabaseManager(this)

        val btnCalendar = findViewById<Button>(R.id.btnCalendar)
        val btnReminders = findViewById<Button>(R.id.btnReminders)
        val btnTasks = findViewById<Button>(R.id.btnTasks)
        val btnTeacherNotes = findViewById<Button>(R.id.btnTeacherNotes)
        recyclerView = findViewById(R.id.recyclerView)

        // Iegūstiet un parādiet demo mājasdarbus
        val homeworkList = databaseManager.getAllHomework()
        homeworkAdapter = HomeworkAdapter(homeworkList) {}
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = homeworkAdapter

        btnCalendar.setOnClickListener {
            startActivity(Intent(this, CalendarActivity::class.java))
        }

        btnReminders.setOnClickListener {
            startActivity(Intent(this, RemindersActivity::class.java))
        }

        btnTasks.setOnClickListener {
            startActivity(Intent(this, TaskTrackingActivity::class.java))
        }

        btnTeacherNotes.setOnClickListener {
            startActivity(Intent(this, TeacherNotesActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        databaseManager.close()
    }
}
