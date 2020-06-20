package com.example.tutorme.viewmodels

import android.app.Application
import com.example.tutorme.model.Tutor
import com.example.tutorme.viewmodels.base.BaseTutorVM

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