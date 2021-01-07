package com.example.edventure.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.edventure.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): LiveData<MutableList<User>>

    @Query("SELECT city FROM user")
    fun findCities(): MutableList<String>

    @Query("SELECT * FROM user WHERE user_id = :userId ")
    suspend fun findById(userId: Long): User

    @Insert
    suspend fun insert(user: User): Long

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)
}