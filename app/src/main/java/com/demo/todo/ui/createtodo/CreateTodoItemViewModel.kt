package com.demo.todo.ui.createtodo

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.todo.data.model.TodoItem
import com.demo.todo.data.repositories.CreateTodoItemRepository
import kotlinx.coroutines.launch

class CreateTodoItemViewModel @ViewModelInject constructor(
    private val createTodoItemRepository: CreateTodoItemRepository
) : ViewModel() {

    fun addTodoItem(todoItem: TodoItem) {
        viewModelScope.launch {
            createTodoItemRepository.addTodoItem(todoItem)
        }
    }

    fun updateTodo(todoItem: TodoItem) {
        viewModelScope.launch {
            createTodoItemRepository.updateTodo(todoItem)
        }
    }

}