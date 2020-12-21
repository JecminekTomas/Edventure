package com.example.edventure.viewmodels

import android.app.Application
import com.example.edventure.model.User
import com.example.edventure.viewmodels.base.BaseUserVM

class UserProfileVM(app: Application) : BaseUserVM(app) {

    suspend fun findById(tutorId: Long): User{
        return userRepository.findById(tutorId)
    }
}