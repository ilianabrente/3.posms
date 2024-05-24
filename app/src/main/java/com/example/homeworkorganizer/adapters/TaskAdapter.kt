package com.example.homeworkorganizer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkorganizer.R
import com.example.homeworkorganizer.model.Task

class TaskAdapter(private var taskList: List<Task>, private val onTaskStatusChange: (Task, String) -> Unit) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    fun updateTaskList(newTaskList: List<Task>) {
        this.taskList = newTaskList
        notifyDataSetChanged()
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        private val dueDateTextView: TextView = itemView.findViewById(R.id.dueDateTextView)
        private val subjectTextView: TextView = itemView.findViewById(R.id.subjectTextView)
        private val statusSpinner: Spinner = itemView.findViewById(R.id.statusSpinner)

        fun bind(task: Task) {
            titleTextView.text = task.title
            descriptionTextView.text = task.description
            dueDateTextView.text = task.dueDate
            subjectTextView.text = task.subject

            val statusAdapter = ArrayAdapter.createFromResource(
                itemView.context,
                R.array.task_status_array,
                android.R.layout.simple_spinner_item
            )
            statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            statusSpinner.adapter = statusAdapter

            statusSpinner.setSelection(
                when (task.status) {
                    "sākts" -> 0
                    "pabeigts" -> 1
                    "atlikts" -> 2
                    else -> 0
                }
            )

            statusSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    val newStatus = parent.getItemAtPosition(position) as String
                    if (newStatus != task.status) {
                        onTaskStatusChange(task, newStatus)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }
    }
}
