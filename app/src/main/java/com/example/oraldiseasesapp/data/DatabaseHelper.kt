package com.example.oraldiseasesapp.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "user.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_USER = "users"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createUserTable = ("CREATE TABLE $TABLE_USER ("
                + "$COLUMN_USERNAME TEXT PRIMARY KEY,"
                + "$COLUMN_PASSWORD TEXT" + ")")

        db?.execSQL(createUserTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        onCreate(db)
    }

    fun registerUser(username: String, password: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("username", username)
        values.put("password", password)
        val result = db.insert("users", null, values)
        db.close()
        return result != -1L
    }

    fun loginUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM users WHERE username = ? AND password = ?"
        val cursor = db.rawQuery(query, arrayOf(username, password))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    fun logOutUser(context: Context) {
        val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun checkUserLoggedIn(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        return sharedPreferences.contains("username")
    }

    @SuppressLint("Range")
    fun getCurrentUser(): User? {
        val db = this.readableDatabase
        val query = "SELECT * FROM users"
        val cursor = db.rawQuery(query, null)
        var user: User? = null
        if (cursor.moveToFirst()) {
            val username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME))
            val password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD))
            user = User(username, password)
        }
        cursor.close()
        db.close()
        return user
    }
}