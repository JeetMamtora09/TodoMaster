package com.demo.todo.ui.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.todo.R
import com.demo.todo.data.models.GenericModel
import com.demo.todo.data.models.HeaderModel
import com.demo.todo.data.models.TodoItem
import com.demo.todo.utils.*
import kotlinx.android.synthetic.main.item_header.view.*
import kotlinx.android.synthetic.main.item_incomplete_todo_list.view.*

class TodoListAdapter(
    private val genericList: ArrayList<GenericModel>,
    private val callBack: (todoItem: TodoItem, action: Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ViewHolderHeader(itemView: View) : RecyclerView.ViewHolder(itemView)
    class ViewHolderInCompletedTask(itemView: View) : RecyclerView.ViewHolder(itemView)
    class ViewHolderCompletedTask(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun updateTodoList(todoList: List<GenericModel>) {
        this@TodoListAdapter.genericList.run {
            clear()
            addAll(todoList)
            notifyDataSetChanged()
        }
    }

    override fun getItemViewType(position: Int): Int {
        genericList[position].run {
            return when {
                this is HeaderModel ->
                    VIEW_TYPE_HEADER
                !(this as TodoItem).isCompleted ->
                    VIEW_TYPE_IN_COMPLETE_TASK
                this.isCompleted ->
                    VIEW_TYPE_COMPLETED_TASK
                else ->
                    VIEW_TYPE_IN_COMPLETE_TASK
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADER ->
                ViewHolderHeader(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_header, parent, false)
                )
            VIEW_TYPE_IN_COMPLETE_TASK ->
                ViewHolderInCompletedTask(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_incomplete_todo_list, parent, false)
                )
            VIEW_TYPE_COMPLETED_TASK ->
                ViewHolderCompletedTask(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_complete_todo_list, parent, false)
                )
            else -> ViewHolderInCompletedTask(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_incomplete_todo_list, parent, false)
            )
        }
    }

    override fun getItemCount(): Int = genericList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val genericModel = genericList[holder.adapterPosition]
        when (holder.itemViewType) {
            VIEW_TYPE_HEADER -> {
                genericModel as HeaderModel
                holder.itemView.tvHeader.text = genericModel.header
            }
            VIEW_TYPE_IN_COMPLETE_TASK -> {
                holder.itemView.run {
                    genericModel as TodoItem
                    tvTitle.text = genericModel.taskName
                    tvDescription.text = genericModel.taskDescription
                    imgDelete.setOnClickListener {
                        callBack.invoke(
                            genericList[holder.adapterPosition] as TodoItem,
                            ACTION_DELETE_TODO
                        )
                    }
                    checkCompleteTodo.isChecked = false
                    checkCompleteTodo.setOnClickListener {
                        val mTodoItem: TodoItem = genericList[holder.adapterPosition] as TodoItem
                        mTodoItem.isCompleted = true
                        callBack.invoke(mTodoItem, ACTION_COMPLETE_TODO)
                    }
                    clContainer.setOnClickListener {
                        callBack.invoke(
                            genericList[holder.adapterPosition] as TodoItem,
                            ACTION_OPEN_TODO
                        )
                    }
                }
            }
            VIEW_TYPE_COMPLETED_TASK -> {
                holder.itemView.run {
                    genericModel as TodoItem
                    tvTitle.text = genericModel.taskName
                    tvDescription.text = genericModel.taskDescription

                    clContainer.setOnClickListener {
                        callBack.invoke(
                            genericList[holder.adapterPosition] as TodoItem,
                            ACTION_OPEN_TODO
                        )
                    }
                    imgDelete.setOnClickListener {
                        callBack.invoke(
                            genericList[holder.adapterPosition] as TodoItem,
                            ACTION_DELETE_TODO
                        )
                    }
                }
            }
        }
    }
}