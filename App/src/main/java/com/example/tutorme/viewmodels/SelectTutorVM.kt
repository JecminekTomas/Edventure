package com.example.tutorme.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.tutorme.model.Tutor
import com.example.tutorme.viewmodels.base.BaseTutorVM

class SelectTutorVM(app: Application): BaseTutorVM(app) {
    fun getAll(): LiveData<MutableList<Tutor>>{
        return tutorRepository.getAll()
    }

    suspend fun findByName(
        firstName: String,
        lastName: String
    ): MutableList<Tutor>{
        return tutorRepository.findByName(firstName, lastName)
    }

    suspend fun findByCity(city: String): MutableList<Tutor>{
        return tutorRepository.findByCity(city)
    }

    suspend fun findByRating(rating: Double): MutableList<Tutor>{
        return tutorRepository.findByRating(rating)
    }

    suspend fun findByPrice(pricePerHour: Double): MutableList<Tutor> {
        return tutorRepository.findByPrice(pricePerHour)
    }

    suspend fun delete(tutor: Tutor){
        tutorRepository.delete(tutor)
    } //TO bude nejspíš právě zde.. Jelikož bude možnost mazat v této aktivitě.

    //TODO: Bude to určitě tady? ... NEBUDE TO až ve FilterActivity? .. A zde bude jenom findByName?
}