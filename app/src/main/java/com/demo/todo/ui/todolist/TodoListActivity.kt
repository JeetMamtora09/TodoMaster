package com.demo.todo.ui.todolist

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.todo.R
import com.demo.todo.data.models.GenericModel
import com.demo.todo.data.models.HeaderModel
import com.demo.todo.data.models.TodoItem
import com.demo.todo.ui.createtodo.CreateTotoItemActivity
import com.demo.todo.utils.*
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class TodoListActivity : AppCompatActivity() {

    private val todoListViewModel: TodoListViewModel by viewModels()
    private lateinit var todoListAdapter: TodoListAdapter

    @Inject
    lateinit var createTotoItemActivity: CreateTotoItemActivity

    @Inject
    lateinit var gson: Gson
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        initObserver()
    }

    private fun initObserver() {
        initTodoListAdapter()
        setObserver()
        btnAddTodoItem.setOnClickListener {
            Intent(this@TodoListActivity, CreateTotoItemActivity::class.java).run {
                startActivity(this)
            }
        }
    }

    private fun initTodoListAdapter() {
        todoListAdapter = TodoListAdapter(arrayListOf()) { todoItem: TodoItem, action: Int ->
            checkActionPerformClick(todoItem, action)
        }
        rvTodoList.run {
            layoutManager = LinearLayoutManager(this@TodoListActivity)
            adapter = todoListAdapter
        }
    }

    private fun checkActionPerformClick(todoItem: TodoItem, action: Int) {
        when (action) {
            ACTION_DELETE_TODO ->
                showTwoButtonAlertDialog(message = getString(R.string.str_msg_confirmation_delete_todo)) {
                    todoListViewModel.deleteTodoItem(todoItem)
                }
            ACTION_COMPLETE_TODO -> {
                todoListViewModel.updateTodo(todoItem)
            }
            ACTION_OPEN_TODO -> {
                openTodoItem(todoItem)
            }
        }
    }

    private fun openTodoItem(todoItem: TodoItem) {
        startActivity(
            Intent(this, createTotoItemActivity::class.java).putExtra(
                EXTRA_TODO_OBJECT,
                gson.toJson(todoItem)
            )
        )
    }

    private fun setObserver() {
        todoListViewModel.getTodoListMutableLiveData().observe(this, Observer {
            val inCompleteTodoList = it.filter { todoItem -> !todoItem.isCompleted }
            val completedTask = it.filter { todoItem -> todoItem.isCompleted }
            val genericModelList = ArrayList<GenericModel>()
            genericModelList.run {
                addAll(inCompleteTodoList)
                if (completedTask.isNotEmpty()) {
                    val headerCompletedTask =
                        HeaderModel(getString(R.string.str_header_name_completed))
                    add(headerCompletedTask)
                    addAll(completedTask)
                }
                todoListAdapter.updateTodoList(this)
            }
        })
    }
}