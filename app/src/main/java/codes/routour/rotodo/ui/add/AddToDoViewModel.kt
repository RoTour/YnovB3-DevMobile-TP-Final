package codes.routour.rotodo.ui.add

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import codes.routour.rotodo.data.Repository
import codes.routour.rotodo.data.local.ToDoDatabase
import codes.routour.rotodo.model.ToDo
import codes.routour.rotodo.ui.main.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*

class AddToDoViewModel(
    private val repository: Repository
) : ViewModel() {
    private val viewModelJob = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)
    var todoTxt = ""
    val eventTodoInserted = MutableLiveData(false)

    fun createTodo(view: View) {
        if (todoTxt.isEmpty()) {
            Snackbar.make(view, "Todo can't be empty", Snackbar.LENGTH_SHORT).show()
            return
        }
        ioScope.launch {
            repository.uploadToRemote(ToDo(todoTxt)) {
                eventTodoInserted.postValue(true)
            }
        }
    }

    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }
}

class AddToDoViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("unchecked_cast")
        if (modelClass.isAssignableFrom(AddToDoViewModel::class.java)) {
            return AddToDoViewModel(repository) as T
        }
        throw IllegalArgumentException("Bad Argument in ListDisplayViewModelFactory. Hint: check in the associated fragment")
    }
}