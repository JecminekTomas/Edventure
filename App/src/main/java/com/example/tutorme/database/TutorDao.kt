package com.example.tutorme.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.tutorme.model.Tutor
import javax.security.auth.Subject

@Dao
interface TutorDao {
    @Query("SELECT * FROM tutor")
    fun getAll(): LiveData<MutableList<Tutor>>

    @Query("SELECT * FROM tutor WHERE tutorId = :id ")
    suspend fun findById(id: Long): Tutor

    @Query("SELECT * FROM tutor WHERE firstName = :firstName AND lastName = :lastName")
    suspend fun findByName(firstName: String, lastName: String): LiveData<MutableList<Tutor>>

    @Query("SELECT * FROM tutor WHERE city = :city")
    suspend fun findByCity(city: String): LiveData<MutableList<Tutor>>

    @Query("SELECT * FROM tutor WHERE rating = :rating ")
    suspend fun findByRating(rating: Double): LiveData<MutableList<Tutor>>

    //TODO: Vymyslet city, subject, ... !!!!
}