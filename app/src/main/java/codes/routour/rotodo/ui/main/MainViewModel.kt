package codes.routour.rotodo.ui.main

import androidx.lifecycle.ViewModel
import codes.routour.rotodo.model.ToDo

class MainViewModel : ViewModel() {
    val todos: MutableList<ToDo> = mutableListOf(
        ToDo("Faire des crêpes"),
        ToDo("Demander de l'argent à Lucas"),
        ToDo("Eteindre le four"),
    )
}