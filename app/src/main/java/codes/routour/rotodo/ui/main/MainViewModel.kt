package codes.routour.rotodo.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import codes.routour.rotodo.data.Repository
import codes.routour.rotodo.data.local.ToDoDatabase
import codes.routour.rotodo.model.ToDo
import kotlinx.coroutines.*

class MainViewModel(
    private val datasource: ToDoDatabase,
    private val repository: Repository
) : ViewModel() {
    private val viewModelJob = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)
    val todos = repository.todos

    init {
        ioScope.launch {
            // Seed Db if empty, then fetch data
//            autoSeeding { loadToDos() }
            repository.refresh(ioScope)
        }

    }

    private suspend fun autoSeeding(callback: suspend () -> Unit) {
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
        callback()
    }

    fun deleteTodo(todo: ToDo) {
        ioScope.launch {
            repository.deleteToDo(todo, ioScope)
        }
    }

    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }
}

class MainViewModelFactory(
    private val datasource: ToDoDatabase,
    private val repository: Repository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("unchecked_cast")
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(datasource, repository) as T
        }
        throw IllegalArgumentException("Bad Argument in ListDisplayViewModelFactory. Hint: check in the associated fragment")
    }
}