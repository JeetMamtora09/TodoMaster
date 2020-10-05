package com.demo.todo.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.demo.todo.data.models.TodoItem

@Dao
interface TodoListDao {

    @Query("SELECT * FROM todoList ORDER BY id DESC")
    fun getAllTodoList(): LiveData<List<TodoItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodoItem(todoItem: TodoItem)

    @Update
    suspend fun updateTodoItem(todoItem: TodoItem)

    @Delete
    suspend fun deleteTodoItem(todoItem: TodoItem)

}