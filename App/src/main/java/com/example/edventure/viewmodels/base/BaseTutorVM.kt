package com.example.edventure.viewmodels.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.edventure.database.repository.ITutorRepository
import com.example.edventure.database.repository.TutorLocalRepoImp

abstract class BaseTutorVM (app: Application) : AndroidViewModel(app){
    protected var tutorRepository: ITutorRepository = TutorLocalRepoImp(app)
}