package com.example.test.mvvm_kotlin.view

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MainAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUI()
        setupViewModel()
        setupDatabase()
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())

        recyclerView.adapter = adapter
    }

    private fun setupViewModel() {

        mainViewModel = ViewModelProviders.of(this, ViewModelFactory(DbHelper(this)))
            .get(MainViewModel::class.java)
    }

    private fun setupDatabase() {
        mainViewModel.getStudents().observe(this, Observer {
            renderList(it)
        })
        mainViewModel.getuiUpdate().observe(this, Observer {
            when (it.id) {
                0 -> throwError(it.msg)
                1 -> clear(it.msg)
                else -> { // Note the block

                }
            }
        })
        mainViewModel.fetchStudents()
    }


    fun saveRecord(view: View) {
        val id = u_id.text.toString()
        val name = u_name.text.toString()
        val mobile = u_mobile.text.toString()
        mainViewModel.addStudent(id, name, mobile)
    }

    //method for updating records based on user id
    fun updateRecord(view: View) {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.update_dialog, null)
        dialogBuilder.setView(dialogView)

        val edtId = dialogView.findViewById(R.id.updateId) as EditText
        val edtName = dialogView.findViewById(R.id.updateName) as EditText
        val edtMobile = dialogView.findViewById(R.id.updateMobile) as EditText

        dialogBuilder.setTitle("Update Record")
        dialogBuilder.setMessage("Enter data below")
        dialogBuilder.setPositiveButton("Update", DialogInterface.OnClickListener { _, _ ->

            val updateId = edtId.text.toString()
            val updateName = edtName.text.toString()
            val updateMobile = edtMobile.text.toString()
            mainViewModel.updateStudent(updateId, updateName, updateMobile)

        })
        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
            //pass
        })
        val b = dialogBuilder.create()
        b.show()
    }

    //method for deleting records based on id
    fun deleteRecord(view: View) {
        //creating AlertDialog for taking user id
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.delete_dialog, null)
        dialogBuilder.setView(dialogView)

        val dltId = dialogView.findViewById(R.id.deleteId) as EditText
        dialogBuilder.setTitle("Delete Record")
        dialogBuilder.setMessage("Enter id below")
        dialogBuilder.setPositiveButton("Delete", DialogInterface.OnClickListener { _, _ ->

            val deleteId = dltId.text.toString()
            mainViewModel.deleteStudent(deleteId)

        })
        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { _, _ ->
            //pass
        })
        val b = dialogBuilder.create()
        b.show()
    }

    private fun throwError(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
    }

    private fun clear(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
        u_id.text.clear()
        u_name.text.clear()
        u_mobile.text.clear()
        mainViewModel.fetchStudents()
    }

    private fun renderList(users: List<StudentDto>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }
}
