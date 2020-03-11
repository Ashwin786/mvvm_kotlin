package com.example.test.mvvm_kotlin.viewmodel

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.mvvm_kotlin.data.model.StudentDto
import com.example.test.mvvm_kotlin.data.model.UIUpdate
import com.mindorks.framework.mvvm.data.repository.MainRepository

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {
    private val students = MutableLiveData<List<StudentDto>>()
    private val uiUpdate = MutableLiveData<UIUpdate>()

    fun fetchStudents() {
        var studentList = mainRepository.getStudents()
        students.postValue(studentList)
    }

    fun getStudents(): MutableLiveData<List<StudentDto>> {
        return students
    }

    fun getuiUpdate(): MutableLiveData<UIUpdate> {
        return uiUpdate
    }

    fun addStudent(id: String, name: String, mobile: String) {
        if (!TextUtils.isEmpty(id.trim()) && !TextUtils.isEmpty(name.trim()) && !TextUtils.isEmpty(mobile.trim())
        ) {
            if(mobile.trim().length!=10){
                uiUpdate.value = UIUpdate(0, "Mobile Number should be 10 digit")
                return
            }
            var status = mainRepository.addStudent(StudentDto(Integer.parseInt(id), name, mobile))
            if (status > -1) {
                uiUpdate.value = UIUpdate(1, "Record Inserted Successfully")
            }else
                uiUpdate.value = UIUpdate(0, "Student record already exist")

        } else {
            uiUpdate.value = UIUpdate(0, "id or name or mobile cannot be blank")
        }


    }

    fun updateStudent(id: String, name: String, mobile: String) {
        if (!TextUtils.isEmpty(id.trim()) && !TextUtils.isEmpty(name.trim()) && !TextUtils.isEmpty(mobile.trim())
        ) {
            var status = mainRepository.updateStudent(StudentDto(Integer.parseInt(id), name, mobile))
            if (status > 0) {
                uiUpdate.value = UIUpdate(1, "Record Updated Successfully")
            }else
                uiUpdate.value = UIUpdate(0, "Student record not available")
        } else {
            uiUpdate.value = UIUpdate(0, "id or name or mobile cannot be blank")
        }


    }

    fun deleteStudent(deleteId: String) {
        if (!TextUtils.isEmpty(deleteId.trim())) {
            var status = mainRepository.deleteStudent(Integer.parseInt(deleteId))
            if (status > -1) {
                uiUpdate.value = UIUpdate(1, "Record Deleted Successfully")
            }
        } else {
            uiUpdate.value = UIUpdate(0, "id cannot be blank")
        }
    }
}
