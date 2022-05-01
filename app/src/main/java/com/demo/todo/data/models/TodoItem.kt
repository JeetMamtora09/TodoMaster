package com.demo.todo.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.demo.todo.utils.TABLENAME

@Entity(tableName = TABLENAME)
data class TodoItem(
    var taskName: String = "",
    var taskDescription: String = "",
    var isCompleted: Boolean = false
):GenericModel() {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}