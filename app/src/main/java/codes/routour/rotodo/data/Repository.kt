package codes.routour.rotodo.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import codes.routour.rotodo.data.local.ToDoDatabase
import codes.routour.rotodo.model.ToDo
import codes.routour.rotodo.model.toToDo
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class Repository(
    private val datasource: ToDoDatabase,
    private val fb: FirebaseFirestore,
) {

    val todos: MutableLiveData<List<ToDo>> = MutableLiveData(mutableListOf())

    suspend fun refresh(ioScope: CoroutineScope) {
        val local = datasource.toDoDao().getAll()
        todos.postValue(local.sortedBy { filter -> filter.id })
        mergeLocalAndRemote(local, ioScope)
    }

    private suspend fun mergeLocalAndRemote(
        local: List<ToDo>,
        ioScope: CoroutineScope,
    ) {
        fb.collection("todos")
            .get()
            .addOnSuccessListener {
                var fbTodos = it.documents.map { doc ->
                    doc.data?.toToDo() ?: ToDo("")
                }
                fbTodos = fbTodos.filter { todo ->
                    todo.text != ""
                }
                val filteredToDos = (fbTodos + local).distinctBy { filter -> filter.id }

                Log.d("DEBUG", local.toString())
                Log.d("DEBUG", fbTodos.toString())
                Log.d("DEBUG", filteredToDos.toString())
                todos.value = filteredToDos.sortedBy { filter -> filter.id }
                ioScope.launch {
                    datasource.toDoDao().insertAll(*filteredToDos.toTypedArray())
                }
            }
    }

    fun getFromServer() {
        Log.d("DEBUG", "Should add todo to firestore")
        fb.collection("todos")
            .get()
            .addOnSuccessListener {
                Log.d("DEBUG", "Firestore fetch Success")
                for (doc in it) {
                    Log.d("DEBUG", doc.data.toString())
                }
                var fbTodos = it.documents.map { doc ->
                    doc.data?.toToDo() ?: ToDo("")
                }
                fbTodos = fbTodos.filter { todo ->
                    todo.text != ""
                }
                todos.value = fbTodos
            }
    }
}