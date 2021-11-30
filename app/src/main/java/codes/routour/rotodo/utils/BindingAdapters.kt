package codes.routour.rotodo.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import codes.routour.rotodo.model.ToDo
import codes.routour.rotodo.ui.main.ToDoListAdapter

@BindingAdapter("listData")
fun bindListData(recyclerView: RecyclerView, listData: List<ToDo>?){
    listData?.let {
        val adapter: ToDoListAdapter = recyclerView.adapter as ToDoListAdapter
        adapter.submitList(listData)
    }
}