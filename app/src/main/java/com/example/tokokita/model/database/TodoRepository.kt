package com.example.tokokita.database

import com.example.tokokita.model.TodoEntity
import kotlinx.coroutines.flow.Flow

class TodoRepository(
    private val todoDao: TodoDao
) {

    fun getAllTodos(): Flow<List<TodoEntity>> {
        return todoDao.getAllTodos()
    }

    suspend fun insert(todo: TodoEntity) {
        todoDao.insert(todo)
    }

    suspend fun update(todo: TodoEntity) {
        todoDao.update(todo)
    }

    suspend fun delete(todo: TodoEntity) {
        todoDao.delete(todo)
    }

    suspend fun deleteAll() {
        todoDao.deleteAll()
    }
}