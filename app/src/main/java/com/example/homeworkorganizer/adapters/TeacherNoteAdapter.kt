package com.example.homeworkorganizer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkorganizer.model.TeacherNote

class TeacherNoteAdapter(private val teacherNoteList: List<TeacherNote>) :
    RecyclerView.Adapter<TeacherNoteAdapter.TeacherNoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherNoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_teacher_note, parent, false)
        return TeacherNoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeacherNoteViewHolder, position: Int) {
        val teacherNote = teacherNoteList[position]
        holder.txtHomeworkId.text = "Mājasdarba ID: ${teacherNote.homeworkId}"
        holder.txtNote.text = "Piezīme: ${teacherNote.note}"
    }

    override fun getItemCount(): Int {
        return teacherNoteList.size
    }

    class TeacherNoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtHomeworkId: TextView = itemView.findViewById(R.id.txtHomeworkId)
        val txtNote: TextView = itemView.findViewById(R.id.txtNote)
    }
}
