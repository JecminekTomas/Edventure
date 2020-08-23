package com.example.edventure.viewmodels

import android.app.Application
import android.provider.ContactsContract
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

    suspend fun findByPrice(pricePerHour: Double): MutableList<Tutor> {
        return tutorRepository.findByPrice(pricePerHour)
    }

    suspend fun delete(tutor: Tutor){
        tutorRepository.delete(tutor)
    } //TO bude nejspíš právě zde.. Jelikož bude možnost mazat v této aktivitě.

    //TODO: Bude to určitě tady? ... NEBUDE TO až ve FilterActivity? .. A zde bude jenom findByName?
}