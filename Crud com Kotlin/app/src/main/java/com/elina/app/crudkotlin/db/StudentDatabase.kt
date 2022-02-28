package com.elina.app.crudkotlin.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.elina.app.crudkotlin.entity.Student


@Database(entities = [Student::class], version = 1, exportSchema = false)
abstract class StudentDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao
}