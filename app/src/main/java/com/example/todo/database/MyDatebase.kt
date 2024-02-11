package com.example.todo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo.database.dao.TodoDao
import com.example.todo.database.model.Todo

@Database(entities = [Todo::class] , version =1 , exportSchema = true)
abstract class MyDatebase : RoomDatabase() {
    abstract fun todoDao() : TodoDao

    companion object{
       private const val DATABASE_NAME = "todo database"
       private var database : MyDatebase? = null
        fun getInstance(context: Context):MyDatebase{
            if (database ==null){
                database = Room.databaseBuilder(context.applicationContext ,
                    MyDatebase::class.java , DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries().build()
            }
            return database!!
        }
    }

}

