package com.example.homeworkorganizer

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkorganizer.adapters.HomeworkSpinnerAdapter
import com.example.homeworkorganizer.adapters.ReminderAdapter
import com.example.homeworkorganizer.database.DatabaseManager
import com.example.homeworkorganizer.model.Homework
import com.example.homeworkorganizer.model.Reminder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class RemindersActivity : AppCompatActivity() {

    private lateinit var databaseManager: DatabaseManager
    private lateinit var recyclerViewReminders: RecyclerView
    private lateinit var reminderAdapter: ReminderAdapter
    private lateinit var fabAddReminder: FloatingActionButton
    private lateinit var reminderList: List<Reminder>
    private lateinit var homeworkList: List<Homework>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminders)
        setTitle("Atgādinājumi")

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        databaseManager = DatabaseManager(this)
        recyclerViewReminders = findViewById(R.id.recyclerViewReminders)
        fabAddReminder = findViewById(R.id.fabAddReminder)

        reminderList = databaseManager.getAllReminders()
        homeworkList = databaseManager.getAllHomework()
        reminderAdapter = ReminderAdapter(reminderList)
        recyclerViewReminders.layoutManager = LinearLayoutManager(this)
        recyclerViewReminders.adapter = reminderAdapter

        fabAddReminder.setOnClickListener {
            showAddReminderDialog()
        }
    }

    private fun showAddReminderDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_reminder, null)
        val spinnerHomework = dialogView.findViewById<Spinner>(R.id.spinnerHomework)
        val datePickerReminder = dialogView.findViewById<DatePicker>(R.id.datePickerReminder)
        val timePickerReminder = dialogView.findViewById<TimePicker>(R.id.timePickerReminder)

        val adapter = HomeworkSpinnerAdapter(this, homeworkList)
        spinnerHomework.adapter = adapter

        val dialog = android.app.AlertDialog.Builder(this)
            .setTitle("Pievienot atgādinājumu")
            .setView(dialogView)
            .setPositiveButton("Pievienot") { _, _ ->
                val selectedHomework = spinnerHomework.selectedItem as Homework
                val year = datePickerReminder.year
                val month = datePickerReminder.month
                val day = datePickerReminder.dayOfMonth
                val hour = timePickerReminder.hour
                val minute = timePickerReminder.minute
                val calendar = Calendar.getInstance().apply {
                    set(year, month, day, hour, minute, 0)
                }
                val reminderTime = String.format("%04d-%02d-%02d %02d:%02d", year, month + 1, day, hour, minute)

                val reminder = Reminder(0, selectedHomework.id, reminderTime, selectedHomework.subject, selectedHomework.description)
                databaseManager.insertReminder(reminder)
                refreshReminderList()
                Toast.makeText(this, "Atgādinājums pievienots", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Atcelt", null)
            .create()

        dialog.show()
    }

    private fun refreshReminderList() {
        reminderList = databaseManager.getAllReminders()
        reminderAdapter.updateReminderList(reminderList)
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
