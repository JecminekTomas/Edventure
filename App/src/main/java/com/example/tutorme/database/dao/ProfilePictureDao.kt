package com.example.tutorme.database.dao

import androidx.room.*
import com.example.tutorme.model.ProfilePicture

@Dao
interface ProfilePictureDao {
    @Query("SELECT * FROM profile_picture WHERE profile_picture_id = :profilePictureId")
    suspend fun getProfilePicture(profilePictureId: Long): ProfilePicture

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profilePicture: ProfilePicture): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(profilePicture: ProfilePicture)

    @Delete
    suspend fun delete(profilePicture: ProfilePicture)
}