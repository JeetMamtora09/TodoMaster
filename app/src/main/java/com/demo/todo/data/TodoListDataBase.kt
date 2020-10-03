package com.demo.todo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.demo.todo.data.dao.TodoListDao
import com.demo.todo.data.model.TodoItem

@Database(
    entities = [TodoItem::class],
    version = 1,
    exportSchema = false
)
abstract class TodoListDataBase : RoomDatabase() {

    abstract fun getNoteDao(): TodoListDao

}