package codes.routour.rotodo.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import codes.routour.rotodo.databinding.TodoListItemBinding
import codes.routour.rotodo.model.ToDo

class ToDoListAdapter : ListAdapter<ToDo, ToDoListAdapter.ToDoListViewHolder>(DiffCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoListViewHolder {
        return ToDoListViewHolder(TodoListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }


    class ToDoListViewHolder(
        private val binding: TodoListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ToDo?) {
            Log.d("DEBUG", "binding item $item")
            binding.itemText.text = item?.text ?: ""
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: ToDoListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<ToDo>() {
        override fun areItemsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
            return oldItem.text == newItem.text
        }

        override fun areContentsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
            return oldItem == newItem
        }
    }
}