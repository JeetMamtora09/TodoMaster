package com.demo.todo.ui.todolist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.todo.data.model.TodoItem
import com.demo.todo.data.repositories.TodoListRepository
import kotlinx.coroutines.launch

class TodoListViewModel @ViewModelInject constructor(
    private val todoListRepository: TodoListRepository
) : ViewModel() {

    fun getTodoListMutableLiveData(): LiveData<List<TodoItem>> =
        todoListRepository.getTodoItemList()

    fun deleteTodoItem(todoItem: TodoItem) {
        viewModelScope.launch {
            todoListRepository.deleteTodo(todoItem)
        }
    }

    fun updateTodo(todoItem: TodoItem) {
        viewModelScope.launch {
            todoListRepository.updateTodo(todoItem)
        }
    }
}
