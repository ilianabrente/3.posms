package com.example.homeworkorganizer

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkorganizer.adapters.HomeworkAdapter
import com.example.homeworkorganizer.database.DatabaseManager
import com.example.homeworkorganizer.model.Homework
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*

class CalendarActivity : AppCompatActivity() {

    private lateinit var databaseManager: DatabaseManager
    private lateinit var calendarView: CalendarView
    private lateinit var recyclerViewHomework: RecyclerView
    private lateinit var homeworkAdapter: HomeworkAdapter
    private lateinit var fabAddHomework: FloatingActionButton
    private lateinit var homeworkList: List<Homework>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        setTitle("Kalendārs")

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        databaseManager = DatabaseManager(this)
        calendarView = findViewById(R.id.calendarView)
        recyclerViewHomework = findViewById(R.id.recyclerViewHomework)
        fabAddHomework = findViewById(R.id.fabAddHomework)

        homeworkList = databaseManager.getAllHomework()
        homeworkAdapter = HomeworkAdapter(homeworkList) { homework ->
            showEditHomeworkDialog(homework)
        }
        recyclerViewHomework.layoutManager = LinearLayoutManager(this)
        recyclerViewHomework.adapter = homeworkAdapter

        markHomeworkDates(homeworkList)

        calendarView.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                val clickedDayCalendar = eventDay.calendar
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val selectedDate = dateFormat.format(clickedDayCalendar.time)
                val filteredHomework = homeworkList.filter { it.dueDate == selectedDate }
                if (filteredHomework.isNotEmpty()) {
                    homeworkAdapter = HomeworkAdapter(filteredHomework) { homework ->
                        showEditHomeworkDialog(homework)
                    }
                    recyclerViewHomework.adapter = homeworkAdapter
                } else {
                    Toast.makeText(this@CalendarActivity, "Nav mājasdarbu šai dienā", Toast.LENGTH_SHORT).show()
                    homeworkAdapter = HomeworkAdapter(emptyList()) { homework ->
                        showEditHomeworkDialog(homework)
                    }
                    recyclerViewHomework.adapter = homeworkAdapter
                }
            }
        })

        fabAddHomework.setOnClickListener {
            showAddHomeworkDialog()
        }
    }

    private fun showAddHomeworkDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_homework, null)
        val editTextTitle = dialogView.findViewById<EditText>(R.id.editTextTitle)
        val editTextSubject = dialogView.findViewById<EditText>(R.id.editTextSubject)
        val editTextDescription = dialogView.findViewById<EditText>(R.id.editTextDescription)
        val datePickerDueDate = dialogView.findViewById<DatePicker>(R.id.datePickerDueDate)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Pievienot mājasdarbu")
            .setView(dialogView)
            .setPositiveButton("Pievienot") { _, _ ->
                val title = editTextTitle.text.toString()
                val subject = editTextSubject.text.toString()
                val description = editTextDescription.text.toString()
                val year = datePickerDueDate.year
                val month = datePickerDueDate.month + 1
                val day = datePickerDueDate.dayOfMonth
                val dueDate = String.format("%04d-%02d-%02d", year, month, day)

                if (title.isNotEmpty() && description.isNotEmpty()) {
                    val newHomework = Homework(0, title, description, dueDate, subject)
                    databaseManager.insertHomework(newHomework)
                    refreshHomeworkList()
                    Toast.makeText(this, "Mājasdarbs pievienots", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Lūdzu, aizpildiet visus laukus", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Atcelt", null)
            .create()

        dialog.show()
    }

    private fun showEditHomeworkDialog(homework: Homework) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_homework, null)
        val editTextTitle = dialogView.findViewById<EditText>(R.id.editTextTitle)
        val editTextSubject = dialogView.findViewById<EditText>(R.id.editTextSubject)
        val editTextDescription = dialogView.findViewById<EditText>(R.id.editTextDescription)
        val datePickerDueDate = dialogView.findViewById<DatePicker>(R.id.datePickerDueDate)
        val buttonDelete = dialogView.findViewById<Button>(R.id.buttonDelete)
        val buttonCancel = dialogView.findViewById<Button>(R.id.buttonCancel)
        val buttonSave = dialogView.findViewById<Button>(R.id.buttonSave)

        editTextTitle.setText(homework.title)
        editTextSubject.setText(homework.subject)
        editTextDescription.setText(homework.description)

        val parts = homework.dueDate.split("-")
        if (parts.size == 3) {
            val year = parts[0].toInt()
            val month = parts[1].toInt() - 1
            val day = parts[2].toInt()
            datePickerDueDate.updateDate(year, month, day)
        }

        val dialog = AlertDialog.Builder(this)
            .setTitle("Rediģēt mājasdarbu")
            .setView(dialogView)
            .create()

        buttonSave.setOnClickListener {
            val title = editTextTitle.text.toString()
            val subject = editTextSubject.text.toString()
            val description = editTextDescription.text.toString()
            val year = datePickerDueDate.year
            val month = datePickerDueDate.month + 1
            val day = datePickerDueDate.dayOfMonth
            val dueDate = String.format("%04d-%02d-%02d", year, month, day)

            if (title.isNotEmpty() && description.isNotEmpty()) {
                val updatedHomework = homework.copy(title = title, subject = subject, description = description, dueDate = dueDate)
                databaseManager.updateHomework(updatedHomework)
                refreshHomeworkList()
                Toast.makeText(this, "Mājasdarbs atjaunināts", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Lūdzu, aizpildiet visus laukus", Toast.LENGTH_SHORT).show()
            }
        }

        buttonCancel.setOnClickListener {
            dialog.dismiss()
        }

        buttonDelete.setOnClickListener {
            databaseManager.deleteHomework(homework.id)
            refreshHomeworkList()
            dialog.dismiss()
            Toast.makeText(this, "Mājasdarbs dzēsts", Toast.LENGTH_SHORT).show()
        }

        dialog.show()
    }

    private fun refreshHomeworkList() {
        homeworkList = databaseManager.getAllHomework()
        homeworkAdapter.updateHomeworkList(homeworkList)
        markHomeworkDates(homeworkList)
    }

    private fun markHomeworkDates(homeworkList: List<Homework>) {
        val events = mutableListOf<EventDay>()
        for (homework in homeworkList) {
            val parts = homework.dueDate.split("-")
            if (parts.size == 3) {
                val year = parts[0].toInt()
                val month = parts[1].toInt() - 1
                val day = parts[2].toInt()
                val calendar = Calendar.getInstance()
                calendar.set(year, month, day)
                events.add(EventDay(calendar, R.drawable.homework_icon))
            }
        }
        calendarView.setEvents(events)
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
