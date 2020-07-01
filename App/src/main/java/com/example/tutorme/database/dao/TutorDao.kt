package com.example.tutorme.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tutorme.model.Tutor

@Dao
interface TutorDao {
    @Query("SELECT * FROM tutor")
    fun getAll(): LiveData<MutableList<Tutor>>

    @Query("SELECT * FROM tutor WHERE tutor_id = :tutorId ")
    suspend fun findById(tutorId: Long): Tutor

    @Query("SELECT * FROM tutor WHERE first_name = :firstName OR last_name = :lastName")
    suspend fun findByName(firstName: String, lastName: String):MutableList<Tutor>

    @Query("SELECT * FROM tutor WHERE city = :city")
    suspend fun findByCity(city: String): MutableList<Tutor>

    @Query("SELECT * FROM tutor WHERE rating >= :rating ")
    suspend fun findByRating(rating: Double): MutableList<Tutor>

    @Query("SELECT * FROM tutor WHERE price_per_hour <= :pricePerHour ")
    suspend fun findByPrice(pricePerHour: Double): MutableList<Tutor>

    @Query("SELECT * FROM tutor WHERE online_lecture = 1")
    suspend fun findByOnline(): MutableList<Tutor>

    @Query("SELECT * FROM tutor WHERE group_lecture = 1")
    suspend fun findByGroup(): MutableList<Tutor>

    @Query("SELECT * FROM tutor WHERE home_lecture = 1")
    suspend fun findByHome(): MutableList<Tutor>

    @Insert
    suspend fun insert(tutor: Tutor): Long

    @Update
    suspend fun update(tutor: Tutor)

    @Delete
    suspend fun delete(tutor: Tutor)
}