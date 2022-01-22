package codes.routour.rotodo.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import codes.routour.rotodo.data.local.ToDoDatabase
import codes.routour.rotodo.model.ToDo
import codes.routour.rotodo.model.toToDo
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Could make a datasource interface to allow dependencies injection (for Testing)
// Server is the main source of trust
class Repository(
    private val localDS: ToDoDatabase,
    private val remoteDS: FirebaseFirestore,
) {

    val todos: MutableLiveData<List<ToDo>> = MutableLiveData(mutableListOf())

    suspend fun refresh(ioScope: CoroutineScope) {
        val local = localDS.toDoDao().getAll()
        todos.postValue(local.sortedBy { filter -> filter.updatedAt })
        updateFromRemote(ioScope)
    }

    private suspend fun updateFromRemote(
        ioScope: CoroutineScope,
    ) {
        remoteDS.collection("todos")
            .get()
            .addOnSuccessListener { query ->
                val fbTodos = query.documents.map { doc ->
                    doc.data?.toToDo() ?: ToDo("")
                }.filter { it.text != "" }
                todos.value = fbTodos

                ioScope.launch {
                    localDS.toDoDao().deleteAll()
                    localDS.toDoDao().insertAll(*fbTodos.toTypedArray())
                }
            }
    }

    fun uploadToRemote(vararg todos: ToDo, next: () -> Unit) {
        todos.forEach { todo ->
            Log.d("DEBUG", "$todo")
            remoteDS.collection("todos").document(todo.id.toString())
                .set(todo)
                .addOnSuccessListener {
                    Log.d("DEBUG", "Uploaded: ${todo.text}")
                    next()
                }
        }
    }

    suspend fun deleteToDo(todo: ToDo, ioScope: CoroutineScope) {
        localDS.toDoDao().delete(todo)
        remoteDS.collection("todos").document(todo.id.toString())
            .delete()
            .addOnSuccessListener {
                ioScope.launch { refresh(ioScope) }
            }
    }
}