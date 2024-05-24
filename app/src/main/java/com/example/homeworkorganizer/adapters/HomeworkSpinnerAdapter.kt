package com.example.homeworkorganizer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.homeworkorganizer.R
import com.example.homeworkorganizer.model.Homework

class HomeworkSpinnerAdapter(context: Context, private val homeworkList: List<Homework>) :
    ArrayAdapter<Homework>(context, 0, homeworkList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(convertView, parent, position)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(convertView, parent, position)
    }

    private fun createViewFromResource(convertView: View?, parent: ViewGroup, position: Int): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_homework_spinner, parent, false)
        val homework = getItem(position)

        val textViewTitle = view.findViewById<TextView>(R.id.textViewTitle)
        val textViewSubject = view.findViewById<TextView>(R.id.textViewSubject)

        if (homework != null) {
            textViewTitle.text = homework.title
            textViewSubject.text = homework.subject
        }

        return view
    }
}
