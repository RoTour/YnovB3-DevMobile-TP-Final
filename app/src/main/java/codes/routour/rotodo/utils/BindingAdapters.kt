package codes.routour.rotodo.utils

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import codes.routour.rotodo.model.ToDo
import codes.routour.rotodo.ui.main.ToDoListAdapter

@BindingAdapter("listData")
fun bindListData(recyclerView: RecyclerView, listData: List<ToDo>?){
    Log.d("DEBUG","Binding List ${listData.toString()}")
    listData?.let {
        val adapter: ToDoListAdapter = recyclerView.adapter as ToDoListAdapter
        adapter.submitList(listData)
    }
}