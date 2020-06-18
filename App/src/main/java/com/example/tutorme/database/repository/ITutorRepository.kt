package com.example.tutorme.database.repository

import androidx.lifecycle.LiveData
import com.example.tutorme.model.Tutor

interface ITutorRepository {

    fun getAll(): LiveData<MutableList<Tutor>>
    suspend fun findById(tutorId: Long): Tutor
    suspend fun findByName(firstName: String, lastName: String): MutableList<Tutor>
    suspend fun findByCity(city: String): MutableList<Tutor>
    suspend fun findByRating(rating: Double): MutableList<Tutor>
    suspend fun findByPrice(pricePerHour: Double): MutableList<Tutor>
    suspend fun insert(tutor: Tutor): Long
    suspend fun update(tutor: Tutor)
    suspend fun delete(tutor: Tutor)
}