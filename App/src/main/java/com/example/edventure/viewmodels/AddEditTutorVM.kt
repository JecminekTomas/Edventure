package com.example.edventure.viewmodels

import android.app.Application
import com.example.edventure.model.Tutor
import com.example.edventure.viewmodels.base.BaseTutorVM

class AddEditTutorVM(app: Application): BaseTutorVM(app) {

    suspend fun findById(tutorId: Long): Tutor{
        return tutorRepository.findById(tutorId)
    }

    suspend fun insert(tutor: Tutor): Long {
        return tutorRepository.insert(tutor)
    }
    suspend fun update(tutor: Tutor){
        return tutorRepository.update(tutor)
    }

}