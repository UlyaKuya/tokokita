package com.example.tokokita.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tokokita.databinding.ItemTodoBinding
import com.example.tokokita.model.TodoEntity

class TodoAdapter(
    private val onSelesaiChange: (TodoEntity) -> Unit,
    private val onEditClick: (TodoEntity) -> Unit,
    private val onHapusClick: (TodoEntity) -> Unit
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private var todos = emptyList<TodoEntity>()

    fun submitList(newTodos: List<TodoEntity>) {
        todos = newTodos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TodoViewHolder {

        val binding = ItemTodoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TodoViewHolder,
        position: Int
    ) {
        holder.bind(todos[position])
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    inner class TodoViewHolder(
        private val binding: ItemTodoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: TodoEntity) {

            binding.tvTodoJudul.text = todo.judul

            binding.checkTodo.setOnCheckedChangeListener(null)
            binding.checkTodo.isChecked = todo.selesai

            binding.checkTodo.setOnCheckedChangeListener { _, checked ->
                if (checked != todo.selesai) {
                    onSelesaiChange(
                        todo.copy(selesai = checked)
                    )
                }
            }

            binding.btnEditTodo.setOnClickListener {
                onEditClick(todo)
            }

            binding.btnHapusTodo.setOnClickListener {
                onHapusClick(todo)
            }
        }
    }
}