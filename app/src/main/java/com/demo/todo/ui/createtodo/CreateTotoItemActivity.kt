package com.demo.todo.ui.createtodo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.demo.todo.R
import com.demo.todo.data.model.TodoItem
import com.demo.todo.utils.EXTRA_TODO_OBJECT
import com.demo.todo.utils.hideKeyboard
import com.demo.todo.utils.showToast
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_create_toto_item.*
import javax.inject.Inject

@AndroidEntryPoint
class CreateTotoItemActivity : AppCompatActivity() {
    private val createTodoItemViewModel: CreateTodoItemViewModel by viewModels()
    @Inject
    lateinit var gson: Gson
    private lateinit var menuItem: MenuItem
    private var todoItem: TodoItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_toto_item)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        todoItem?.run {
            if (isCompleted) return false
            title = "Completed Todo"
        }
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_create_todo_item, menu)
        menu?.run {
            menuItem = findItem(R.id.actionDone)
            if (todoItem != null) {
                menuItem.isEnabled = false
                menuItem.icon.alpha = 150
                title = "Edit Todo"
            }
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun init() {
        title = "Create Todo"
        if (intent.hasExtra(EXTRA_TODO_OBJECT))
            todoItem =
                gson.fromJson(
                    intent.getStringExtra(EXTRA_TODO_OBJECT).toString(),
                    TodoItem::class.java
                )
        checkForEdit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun checkForEdit() {
        if (todoItem == null) return
        todoItem?.run {
            edtDescription.setText(taskDescription)
            edtTitle.setText(taskName)
            if (isCompleted) {
                edtDescription.isFocusable = false
                edtTitle.isFocusable = false
                edtTitle.isEnabled = false
                edtDescription.isEnabled = false
            }
            enableDiableMenuItem()
        }
    }

    private fun enableDiableMenuItem() {
        edtTitle.doAfterTextChanged {
            checkForEdit(it.toString(), edtDescription.text.toString())
        }
        edtDescription.doAfterTextChanged {
            checkForEdit(edtTitle.text.toString(), it.toString())
        }
    }

    private fun checkForEdit(edtTitleStr: String, edtDescription: String) {
        todoItem?.run {
            if ((edtTitleStr == taskName) and (edtDescription == taskDescription)) {
                menuItem.isEnabled = false
                menuItem.icon.alpha = 150
            } else {
                menuItem.isEnabled = true
                menuItem.icon.alpha = 255
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionDone -> {
                if (isValid()) {
                    edtDescription.hideKeyboard()
                    if (todoItem == null)
                        saveTodoItem()
                    else
                        editTodoItem()
                    finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun editTodoItem() {
        todoItem?.run {
            taskName = edtTitle.text.toString()
            taskDescription = edtDescription.text.toString()
            createTodoItemViewModel.updateTodo(this)
        }
        showToast("Todo item updated.")
    }

    private fun saveTodoItem() {
        val todoItem = TodoItem(
            taskName = edtTitle.text.toString(),
            taskDescription = edtDescription.text.toString()
        )
        createTodoItemViewModel.addTodoItem(todoItem)
        showToast("Todo item created.")
    }

    private fun isValid(): Boolean {
        if (edtTitle.text.toString().isEmpty()) {
            inputEdtTitle.error = "Please provide Title."
            return false
        }
        if (edtDescription.text.toString().isEmpty()) {
            inputEdtDescription.error = "Please provide Description."
            return false
        }
        return true
    }
}