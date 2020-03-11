package com.mindorks.framework.mvvm.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.test.mvvm_kotlin.data.db.DbHelper
import com.example.test.mvvm_kotlin.viewmodel.MainViewModel
import com.mindorks.framework.mvvm.data.repository.MainRepository

class ViewModelFactory(private val dbHelper: DbHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(dbHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}