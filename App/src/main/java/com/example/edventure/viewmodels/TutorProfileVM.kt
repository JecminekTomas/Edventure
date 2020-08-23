package com.example.edventure.viewmodels

import android.app.Application
import com.example.edventure.model.Tutor
import com.example.edventure.viewmodels.base.BaseTutorVM

class TutorProfileVM(app: Application) : BaseTutorVM(app) {

    suspend fun findById(tutorId: Long): Tutor{
        return tutorRepository.findById(tutorId)
    }
}