package com.example.test.mvvm_kotlin.data.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.test.mvvm_kotlin.data.model.StudentDto

class DbHelper(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_STUDENTS_TABLE = ("CREATE TABLE " + TABLE_STUDENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_MOBILE + " VARCHAR(10)" + ")")
        db?.execSQL(CREATE_STUDENTS_TABLE)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "StudentsDatabase"
        private val TABLE_STUDENTS = "StudentsTable"
        private val KEY_ID = "id"
        private val KEY_NAME = "name"
        private val KEY_MOBILE = "mobile"
    }


    //method to insert data  
    fun addstudents(emp: StudentDto):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.studentId)
        contentValues.put(KEY_NAME, emp.studentName) // StudentDto Name  
        contentValues.put(KEY_MOBILE,emp.studentMobile ) // StudentDto Phone
        // Inserting Row  
        val success = db.insert(TABLE_STUDENTS, null, contentValues)
        //2nd argument is String containing nullColumnHack  
        db.close() // Closing database connection  
        return success
    }
    //method to read data  
    fun getStudents():List<StudentDto>{
        val empList:ArrayList<StudentDto> = ArrayList<StudentDto>()
        val selectQuery = "SELECT  * FROM $TABLE_STUDENTS"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var studentId: Int
        var studentName: String
        var studentMobile: String
        if (cursor.moveToFirst()) {
            do {
                studentId = cursor.getInt(cursor.getColumnIndex("id"))
                studentName = cursor.getString(cursor.getColumnIndex("name"))
                studentMobile = cursor.getString(cursor.getColumnIndex("mobile"))
                val emp= StudentDto(studentId, studentName, studentMobile)
                empList.add(emp)
            } while (cursor.moveToNext())
        }
        return empList
    }
    //method to update data  
    fun updateStudent(emp: StudentDto):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.studentId)
        contentValues.put(KEY_NAME, emp.studentName) // StudentDto Name  
        contentValues.put(KEY_MOBILE,emp.studentMobile ) // StudentDto Email

        // Updating Row  
        val success = db.update(TABLE_STUDENTS, contentValues,"id="+emp.studentId,null)
        //2nd argument is String containing nullColumnHack  
        db.close() // Closing database connection  
        return success
    }
    //method to delete data  
    fun deleteStudent(studentId: Int):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, studentId) // StudentDto studentId
        // Deleting Row  
        val success = db.delete(TABLE_STUDENTS,"id="+ studentId,null)
        //2nd argument is String containing nullColumnHack  
        db.close() // Closing database connection  
        return success
    }
}