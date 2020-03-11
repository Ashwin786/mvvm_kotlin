package com.mindorks.framework.mvvm.data.repository

import com.example.test.mvvm_kotlin.data.db.DbHelper
import com.example.test.mvvm_kotlin.data.model.StudentDto

class MainRepository(private val dbHelper: DbHelper) {

    fun getStudents(): List<StudentDto> {
        return dbHelper.getStudents()
    }

    fun addStudent(studentDto: StudentDto): Long {
        return dbHelper.addstudents(studentDto)
    }

}