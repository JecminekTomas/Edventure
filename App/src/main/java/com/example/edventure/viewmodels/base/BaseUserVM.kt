package com.example.edventure.viewmodels.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.edventure.database.repository.IUserRepository
import com.example.edventure.database.repository.UserLocalRepoImpl

abstract class BaseUserVM (app: Application) : AndroidViewModel(app){
    protected var userRepository: IUserRepository = UserLocalRepoImpl(app)
}