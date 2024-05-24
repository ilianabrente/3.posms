package com.example.homeworkorganizer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkorganizer.R
import com.example.homeworkorganizer.model.Homework

class HomeworkAdapter(
    private var homeworkList: List<Homework>,
    private val onHomeworkClick: (Homework) -> Unit
) : RecyclerView.Adapter<HomeworkAdapter.HomeworkViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeworkViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_homework, parent, false)
        return HomeworkViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeworkViewHolder, position: Int) {
        val homework = homeworkList[position]
        holder.bind(homework)
    }

    override fun getItemCount(): Int {
        return homeworkList.size
    }

    fun updateHomeworkList(newHomeworkList: List<Homework>) {
        this.homeworkList = newHomeworkList
        notifyDataSetChanged()
    }

    inner class HomeworkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val subjectTextView: TextView = itemView.findViewById(R.id.subjectTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        private val dueDateTextView: TextView = itemView.findViewById(R.id.dueDateTextView)

        fun bind(homework: Homework) {
            titleTextView.text = homework.title
            subjectTextView.text = "Priekšmets: ${homework.subject}"
            descriptionTextView.text = "Uzdevums: ${homework.description}"
            dueDateTextView.text = "Jānodod līdz: ${homework.dueDate}"

            itemView.setOnClickListener {
                onHomeworkClick(homework)
            }
        }
    }
}
