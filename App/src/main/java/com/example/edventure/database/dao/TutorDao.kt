package com.example.edventure.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.edventure.model.Tutor

@Dao
interface TutorDao {
    @Query("SELECT * FROM tutor")
    fun getAll(): LiveData<MutableList<Tutor>>

    @Query("SELECT city FROM tutor")
    fun findCities(): MutableList<String>

    @Query("SELECT * FROM tutor WHERE tutor_id = :tutorId ")
    suspend fun findById(tutorId: Long): Tutor

    @Query("SELECT * FROM tutor WHERE first_name = :firstName OR last_name = :lastName")
    suspend fun findByName(firstName: String, lastName: String):MutableList<Tutor>

    @Query("SELECT * FROM tutor WHERE city = :city")
    suspend fun findByCity(city: String): MutableList<Tutor>

    @Query("SELECT * FROM tutor WHERE rating >= :rating ")
    suspend fun findByRating(rating: Double): MutableList<Tutor>

    @Query("SELECT * FROM tutor WHERE price_per_hour <= :pricePerHour ")
    suspend fun findByPriceLowerThan(pricePerHour: Double): MutableList<Tutor>

    @Query("SELECT * FROM tutor WHERE price_per_hour >= :pricePerHour")
    suspend fun findByPriceHigherThan(pricePerHour: Double): MutableList<Tutor>

    @Insert
    suspend fun insert(tutor: Tutor): Long

    @Update
    suspend fun update(tutor: Tutor)

    @Delete
    suspend fun delete(tutor: Tutor)
}