package com.example.edventure.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.edventure.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): LiveData<MutableList<User>>

    @Query("SELECT city FROM user")
    fun findCities(): MutableList<String>

    @Query("SELECT * FROM user WHERE user_id = :userId ")
    suspend fun findById(userId: Long): User

    @Query("SELECT * FROM user WHERE first_name = :firstName OR last_name = :lastName")
    suspend fun findByName(firstName: String, lastName: String):MutableList<User>

    @Query("SELECT * FROM user WHERE city = :city")
    suspend fun findByCity(city: String): MutableList<User>

    @Query("SELECT * FROM user WHERE rating >= :rating ")
    suspend fun findByRating(rating: Double): MutableList<User>

    @Query("SELECT * FROM user WHERE price_per_hour <= :pricePerHour ")
    suspend fun findByPriceLowerThan(pricePerHour: Double): MutableList<User>

    @Query("SELECT * FROM user WHERE price_per_hour >= :pricePerHour")
    suspend fun findByPriceHigherThan(pricePerHour: Double): MutableList<User>

    @Insert
    suspend fun insert(user: User): Long

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)
}