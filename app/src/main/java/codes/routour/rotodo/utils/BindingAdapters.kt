package codes.routour.rotodo.utils

import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import codes.routour.rotodo.model.ToDo
import codes.routour.rotodo.ui.main.ToDoListAdapter
import android.graphics.Paint
import android.widget.CheckBox


@BindingAdapter("listData")
fun bindListData(recyclerView: RecyclerView, listData: List<ToDo>?){
    listData?.let {
        val adapter: ToDoListAdapter = recyclerView.adapter as ToDoListAdapter
        adapter.submitList(listData)
    }
}

@BindingAdapter("completed")
fun TextView.bindCompleted(toDo: ToDo?) {
    toDo?.let {
        if (toDo.completed) {
            Log.d("DEBUG", "(Painting) Todo ${toDo.text} is ${toDo.completed}")
            this.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG
        } else {
            Log.d("DEBUG", "(Removing) Todo ${toDo.text} is ${toDo.completed}")
            this.paintFlags = this.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }
}

@BindingAdapter("checkedState")
fun CheckBox.setChecked(todo: ToDo?) {
    todo?.let {
        this.isChecked = it.completed
    }
}