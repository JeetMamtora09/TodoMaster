package com.demo.todo.ui.createtodo

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.todo.data.models.TodoItem
import com.demo.todo.data.repository.TodoListRepository
import kotlinx.coroutines.launch

class CreateTodoItemViewModel @ViewModelInject constructor(
    private val todoListRepository: TodoListRepository
) : ViewModel() {

    fun addTodoItem(todoItem: TodoItem) {
        viewModelScope.launch {
            todoListRepository.addTodoItem(todoItem)
        }
    }

    fun updateTodo(todoItem: TodoItem) {
        viewModelScope.launch {
            todoListRepository.updateTodo(todoItem)
        }
    }

}