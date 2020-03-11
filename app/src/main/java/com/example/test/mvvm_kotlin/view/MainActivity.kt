package com.example.test.mvvm_kotlin.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.mvvm_kotlin.R
import com.example.test.mvvm_kotlin.data.db.DbHelper
import com.example.test.mvvm_kotlin.data.model.StudentDto
import com.example.test.mvvm_kotlin.viewmodel.MainViewModel
import com.mindorks.framework.mvvm.ui.base.ViewModelFactory
import com.mindorks.framework.mvvm.ui.main.adapter.MainAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUI()
        setupViewModel()
        setupDatabase()
        addStudent()
    }

    private fun addStudent() {

        mainViewModel.addStudent(StudentDto(1,"Ramesh","rkdev"))
    }

    private fun setupDatabase() {
        mainViewModel.getStudents().observe(this, Observer {
            renderList(it)
        })
        mainViewModel.fetchStudents()
    }
    private fun renderList(users: List<StudentDto>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }
    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MainAdapter
    private fun setupViewModel() {

        mainViewModel = ViewModelProviders.of(this, ViewModelFactory(DbHelper(this))).get(MainViewModel::class.java)
    }

}
