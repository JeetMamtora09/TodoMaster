package com.demo.todo.data.repository

import androidx.lifecycle.LiveData
import com.demo.todo.data.dao.TodoListDao
import com.demo.todo.data.models.TodoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TodoListRepository @Inject constructor(private val todoListDao: TodoListDao) {

    fun getTodoItemList(): LiveData<List<TodoItem>> =
        todoListDao.getAllTodoList()

    suspend fun addTodoItem(todoItem: TodoItem) {
        withContext(Dispatchers.IO) {
            todoListDao.insertTodoItem(todoItem)
        }
    }

    suspend fun updateTodo(todoItem: TodoItem) {
        withContext(Dispatchers.IO) {
            todoListDao.updateTodoItem(todoItem)
        }
    }

    suspend fun deleteTodo(todoItem: TodoItem) {
        withContext(Dispatchers.IO) {
            todoListDao.deleteTodoItem(todoItem)
        }
    }

}