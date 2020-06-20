package com.example.tutorme.viewmodels.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.tutorme.database.repository.ITutorRepository
import com.example.tutorme.database.repository.TutorLocalRepoImp

abstract class BaseTutorVM (app: Application) : AndroidViewModel(app){
    protected var tutorRepository: ITutorRepository = TutorLocalRepoImp(app)
}