package com.example.todo.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todo.database.MyDatebase
import com.example.todo.database.model.Todo
import com.example.todo.databinding.ItemTodoBinding

class TodoAdapter(private var todos: List<Todo>) : Adapter<TodoAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = todos?.get(position) ?:return
        holder.binding.todoTitleTv.text = todos[position].title
        holder.binding.todoDescriptionTv.text = todos[position].description
        holder.itemView.setOnClickListener {
            onTaskClickListener?.onTaskClicked(item)
        }
        holder.binding.leftItem.setOnClickListener {
            MyDatebase.getInstance(holder.binding.root.context).todoDao().delete(item)

        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }


   private var onTaskClickListener: OnTaskClickListener? = null
    fun setOnTaskClickListener(listener:OnTaskClickListener){
        onTaskClickListener = listener

    }



    fun interface OnTaskClickListener{
        fun onTaskClicked(todo : Todo)
    }

    fun updateTodos(newTodosList: List<Todo>){
        todos = newTodosList
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root) {

    }

}