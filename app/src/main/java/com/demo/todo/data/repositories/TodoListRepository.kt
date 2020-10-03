package com.demo.todo.data.repositories

import androidx.lifecycle.LiveData
import com.demo.todo.data.dao.TodoListDao
import com.demo.todo.data.model.TodoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TodoListRepository @Inject constructor(private val todoListDao: TodoListDao) {

    fun getTodoItemList(): LiveData<List<TodoItem>> =
        todoListDao.getAllTodoList()

    suspend fun deleteTodo(todoItem: TodoItem) {
        withContext(Dispatchers.IO) {
            todoListDao.deleteTodoItem(todoItem)
        }
    }

    suspend fun updateTodo(todoItem: TodoItem) {
        withContext(Dispatchers.IO) {
            todoListDao.updateTodoItem(todoItem)
        }
    }
}