package com.example.edventure.viewmodels

import android.app.Application
import com.example.edventure.model.User
import com.example.edventure.viewmodels.base.BaseUserVM

class AddEditUserVM(app: Application): BaseUserVM(app) {

    suspend fun findById(tutorId: Long): User{
        return userRepository.findById(tutorId)
    }

    suspend fun insert(user: User): Long {
        return userRepository.insert(user)
    }
    suspend fun update(user: User){
        return userRepository.update(user)
    }

}