package com.example.test.mvvm_kotlin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.mvvm_kotlin.data.model.StudentDto
import com.mindorks.framework.mvvm.data.repository.MainRepository

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {
    private val students = MutableLiveData<List<StudentDto>>()

    fun fetchStudents() {
        var studentList = mainRepository.getStudents()
        students.postValue(studentList)
    }

    fun getStudents(): MutableLiveData<List<StudentDto>> {
        return students
    }

    fun addStudent(studentDto: StudentDto) {
        mainRepository.addStudent(studentDto)
    }
}
