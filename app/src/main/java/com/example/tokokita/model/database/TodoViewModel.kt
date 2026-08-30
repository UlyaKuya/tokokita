package com.example.tokokita.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tokokita.database.AppDatabase
import com.example.tokokita.database.TodoRepository
import com.example.tokokita.model.TodoEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) :
    AndroidViewModel(application) {

    private val database =
        AppDatabase.getDatabase(application)

    private val repository =
        TodoRepository(database.todoDao())

    val todos: StateFlow<List<TodoEntity>> =
        repository.getAllTodos()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    fun tambahTodo(judul: String) {

        if (judul.isBlank()) return

        viewModelScope.launch {

            repository.insert(
                TodoEntity(
                    judul = judul
                )
            )
        }
    }

    fun updateTodo(todo: TodoEntity) {

        viewModelScope.launch {
            repository.update(todo)
        }
    }

    fun hapusTodo(todo: TodoEntity) {

        viewModelScope.launch {
            repository.delete(todo)
        }
    }

    fun hapusSemuaTodo() {

        viewModelScope.launch {
            repository.deleteAll()
        }
    }
}