package codes.routour.rotodo.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import codes.routour.rotodo.data.local.ToDoDatabase
import codes.routour.rotodo.model.ToDo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val datasource: ToDoDatabase) : ViewModel() {
    private val ioScope = Dispatchers.IO
    val todos: MutableLiveData<List<ToDo>> = MutableLiveData(mutableListOf())
    init {
        viewModelScope.launch {
            // Seed Db if empty, then fetch data
            autoSeeding { withContext(ioScope) { loadToDos() } }
        }
    }

    private suspend fun autoSeeding(callback: suspend () -> Unit) {
        withContext(ioScope) {
            var dbToDos = datasource.toDoDao().getAll()
            if (dbToDos.isEmpty()) {
                datasource.toDoDao().insertAll(
                    ToDo("Faire des crêpes"),
                    ToDo("Demander de l'argent à Lucas"),
                    ToDo("Éteindre le four"),
                )
                Log.d("DEBUG", "Seeded db")
                dbToDos = datasource.toDoDao().getAll()
            }
            Log.d("DEBUG", dbToDos.toString())
            Log.d("DEBUG", "Done fetching from db")
        }
        callback()
    }

    private suspend fun loadToDos() {
        withContext(ioScope) {
            todos.postValue(datasource.toDoDao().getAll())
        }
    }

    fun deleteTodo(todo: ToDo) {
        viewModelScope.launch {
            withContext(ioScope) {
                datasource.toDoDao().delete(todo)
                loadToDos()
            }
        }
    }
}

class MainViewModelFactory(
    private val datasource: ToDoDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("unchecked_cast")
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(datasource) as T
        }
        throw IllegalArgumentException("Bad Argument in ListDisplayViewModelFactory. Hint: check in the associated fragment")
    }
}