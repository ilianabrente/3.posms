package com.example.homeworkorganizer

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkorganizer.adapters.TaskAdapter
import com.example.homeworkorganizer.database.DatabaseManager
import com.example.homeworkorganizer.model.Task

class TaskTrackingActivity : AppCompatActivity() {

    private lateinit var databaseManager: DatabaseManager
    private lateinit var recyclerViewTasks: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var taskList: List<Task>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_tracking)
        setTitle("Uzdevumu izsekošana")

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        databaseManager = DatabaseManager(this)
        recyclerViewTasks = findViewById(R.id.recyclerViewTasks)

        taskList = databaseManager.getAllTasks()
        taskAdapter = TaskAdapter(taskList) { task, newStatus ->
            updateTaskStatus(task, newStatus)
        }
        recyclerViewTasks.layoutManager = LinearLayoutManager(this)
        recyclerViewTasks.adapter = taskAdapter
    }

    private fun updateTaskStatus(task: Task, newStatus: String) {
        val updatedTask = task.copy(status = newStatus)
        databaseManager.updateTask(updatedTask)
        Toast.makeText(this, "Uzdevuma statuss atjaunināts", Toast.LENGTH_SHORT).show()
        refreshTaskList()
    }

    private fun refreshTaskList() {
        taskList = databaseManager.getAllTasks()
        taskAdapter.updateTaskList(taskList)
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
