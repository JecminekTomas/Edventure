package com.example.edventure.viewmodels

import android.app.Application
import com.example.edventure.model.Tutor
import com.example.edventure.viewmodels.base.BaseTutorVM

class FilterTutorVM(app: Application): BaseTutorVM(app) {

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

}