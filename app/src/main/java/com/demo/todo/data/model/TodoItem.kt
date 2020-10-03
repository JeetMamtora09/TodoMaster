package com.demo.todo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todoList")
data class TodoItem(
    var taskName: String = "",
    var taskDescription: String = "",
    var isCompleted: Boolean = false
):GenericModel() {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}