package com.example.edventure.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.edventure.model.ProfilePicture
import com.example.edventure.model.User
import com.example.edventure.viewmodels.base.BaseUserVM

class SelectUserVM(app: Application): BaseUserVM(app) {
    fun getAll(): LiveData<MutableList<User>>{
        return userRepository.getAll()
    }

    suspend fun findById(tutorId: Long): User{
        return userRepository.findById(tutorId)
    }

    suspend fun findProfilePicture(tutorId: Long): ProfilePicture{
        return userRepository.findProfilePicture(tutorId)
    }

    suspend fun findByName(
        firstName: String,
        lastName: String
    ): MutableList<User>{
        return userRepository.findByName(firstName, lastName)
    }


    suspend fun findByCity(city: String): MutableList<User>{
        return userRepository.findByCity(city)
    }

    suspend fun findByRating(rating: Double): MutableList<User>{
        return userRepository.findByRating(rating)
    }

    suspend fun findByPriceLowerThan(pricePerHour: Double): MutableList<User> {
        return userRepository.findByPriceLowerThan(pricePerHour)
    }

    suspend fun findByPriceHigherThan(pricePerHour: Double): MutableList<User> {
        return userRepository.findByPriceHigherThan(pricePerHour)
    }

    suspend fun delete(user: User){
        userRepository.delete(user)
    }
}