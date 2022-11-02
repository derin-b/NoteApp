package com.example.noteapp.data

import androidx.lifecycle.LiveData
import androidx.room.*

import androidx.room.Update
import com.example.noteapp.model.Entity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(noteEntity: Entity)

    @Update
    suspend fun update(noteEntity: Entity)

    @Delete
    suspend fun delete(noteEntity: Entity)

    @Query("SELECT * FROM noteTable WHERE title LIKE :searchQuery OR noteText LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<Entity>>

    @Query("SELECT * FROM `noteTable` ORDER BY id ASC")
    fun getAllNotes(): Flow<List<Entity>>
}