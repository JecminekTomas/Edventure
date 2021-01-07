package com.example.edventure.database.repository

import androidx.lifecycle.LiveData
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.edventure.model.ProfilePicture
import com.example.edventure.model.User

interface IUserRepository {

    fun getAll(): LiveData<MutableList<User>>
    fun findCities(): MutableList<String>
    suspend fun findById(userId: Long): User
    suspend fun findProfilePicture(userId: Long): ProfilePicture
    suspend fun insert(user: User): Long
    suspend fun update(user: User)
    suspend fun delete(user: User)
}