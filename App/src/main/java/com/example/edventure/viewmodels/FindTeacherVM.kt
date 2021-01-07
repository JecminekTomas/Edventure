package com.example.edventure.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.edventure.model.ProfilePicture
import com.example.edventure.model.User
import com.example.edventure.viewmodels.base.BaseUserVM

class FindTeacherVM(app: Application): BaseUserVM(app) {
    fun getAll(): LiveData<MutableList<User>>{
        return userRepository.getAll()
    }

    suspend fun findById(tutorId: Long): User{
        return userRepository.findById(tutorId)
    }

    suspend fun findProfilePicture(tutorId: Long): ProfilePicture{
        return userRepository.findProfilePicture(tutorId)
    }

    suspend fun delete(user: User){
        userRepository.delete(user)
    }
}