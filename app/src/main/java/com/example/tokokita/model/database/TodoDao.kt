package com.example.tokokita.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.tokokita.model.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert
    suspend fun insert(todo: TodoEntity)

    @Query("SELECT * FROM todos ORDER BY id DESC")
    fun getAllTodos(): Flow<List<TodoEntity>>

    @Update
    suspend fun update(todo: TodoEntity)

    @Delete
    suspend fun delete(todo: TodoEntity)

    @Query("DELETE FROM todos")
    suspend fun deleteAll()
}