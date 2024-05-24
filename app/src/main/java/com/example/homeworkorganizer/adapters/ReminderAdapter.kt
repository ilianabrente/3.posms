package com.example.homeworkorganizer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkorganizer.R
import com.example.homeworkorganizer.model.Reminder

class ReminderAdapter(private var reminderList: List<Reminder>) :
    RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reminder, parent, false)
        return ReminderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val reminder = reminderList[position]
        holder.bind(reminder)
    }

    override fun getItemCount(): Int {
        return reminderList.size
    }

    fun updateReminderList(newReminderList: List<Reminder>) {
        this.reminderList = newReminderList
        notifyDataSetChanged()
    }

    inner class ReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val subjectTextView: TextView = itemView.findViewById(R.id.subjectTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        private val reminderTimeTextView: TextView = itemView.findViewById(R.id.reminderTimeTextView)

        fun bind(reminder: Reminder) {
            subjectTextView.text = reminder.subject
            descriptionTextView.text = reminder.description
            reminderTimeTextView.text = reminder.reminderDate
        }
    }
}
