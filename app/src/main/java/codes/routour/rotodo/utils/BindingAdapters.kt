package codes.routour.rotodo.utils

import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import codes.routour.rotodo.model.ToDo
import codes.routour.rotodo.ui.main.ToDoListAdapter
import android.graphics.Paint




@BindingAdapter("listData")
fun bindListData(recyclerView: RecyclerView, listData: List<ToDo>?){
    listData?.let {
        val adapter: ToDoListAdapter = recyclerView.adapter as ToDoListAdapter
        adapter.submitList(listData)
    }
}

@BindingAdapter("completed")
fun bindCompleted(textView: TextView, toDo: ToDo?) {
    toDo?.let {
        Log.d("DEBUG", "Todo ${toDo.text} is ${toDo.completed}")
        if (toDo.completed) {
            textView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
        } else {
            textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }
}