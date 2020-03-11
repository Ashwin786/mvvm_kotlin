package com.mindorks.framework.mvvm.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test.mvvm_kotlin.R
import com.example.test.mvvm_kotlin.data.model.StudentDto
import kotlinx.android.synthetic.main.item_layout.view.*

class MainAdapter(
    private val students: ArrayList<StudentDto>
) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dto: StudentDto) {
            itemView.textViewUserId.text = Integer.toString(dto.studentId)
            itemView.textViewUserName.text = dto.studentName
            itemView.textViewUserEmail.text = dto.studentMobile

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = students.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(students[position])

    fun addData(list: List<StudentDto>) {
        students.clear()
        students.addAll(list)
    }

}