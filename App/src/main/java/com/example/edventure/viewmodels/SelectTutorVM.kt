package com.example.edventure.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.edventure.model.ProfilePicture
import com.example.edventure.model.Tutor
import com.example.edventure.viewmodels.base.BaseTutorVM

class SelectTutorVM(app: Application): BaseTutorVM(app) {
    fun getAll(): LiveData<MutableList<Tutor>>{
        return tutorRepository.getAll()
    }

    suspend fun findById(tutorId: Long): Tutor{
        return tutorRepository.findById(tutorId)
    }

    suspend fun findProfilePicture(tutorId: Long): ProfilePicture{
        return tutorRepository.findProfilePicture(tutorId)
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

    suspend fun findByPriceLowerThan(pricePerHour: Double): MutableList<Tutor> {
        return tutorRepository.findByPriceLowerThan(pricePerHour)
    }

    suspend fun findByPriceHigherThan(pricePerHour: Double): MutableList<Tutor> {
        return tutorRepository.findByPriceHigherThan(pricePerHour)
    }

    suspend fun delete(tutor: Tutor){
        tutorRepository.delete(tutor)
    }
}