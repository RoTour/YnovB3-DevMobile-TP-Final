package codes.routour.rotodo.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import codes.routour.rotodo.databinding.TodoListItemBinding
import codes.routour.rotodo.model.ToDo

class ToDoListAdapter(
    private val clickListener: TodoClickListener
) : ListAdapter<ToDo, ToDoListAdapter.ToDoListViewHolder>(DiffCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoListViewHolder {
        return ToDoListViewHolder(TodoListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    fun getItemAt(position: Int): ToDo? {
        return getItem(position)
    }
    class ToDoListViewHolder(
        private val binding: TodoListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ToDo?, clickListener: TodoClickListener) {
            item?.let {
                binding.itemText.text = item.text
                binding.todo = item
            }
            binding.clickListener = clickListener
            binding.viewHolder = this
            binding.executePendingBindings()
            // Fixing a bug in the RecyclerView lib where items layout params are forced
            // to be wrap_content
            itemView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

    }

    override fun onBindViewHolder(holder: ToDoListViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
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

class TodoClickListener(
    private val clickListener:
        ((task: ToDo, viewHolder: ToDoListAdapter.ToDoListViewHolder) -> Unit)
) {
    fun onClick(task: ToDo, viewHolder: ToDoListAdapter.ToDoListViewHolder) =
        clickListener(task, viewHolder)
}
