package com.demo.todo.data.repositories

import com.demo.todo.data.dao.TodoListDao
import com.demo.todo.data.model.TodoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CreateTodoItemRepository @Inject constructor(private val todoListDao: TodoListDao) {
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
}