package com.example.tutorme.database.dao

import androidx.room.*
import com.example.tutorme.model.TutorAvatar

@Dao
interface TutorAvatarDao {
    @Query("SELECT * FROM avatar WHERE avatar_id = :avatarId")
    suspend fun getAvatar(avatarId: Long): TutorAvatar

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(avatar: TutorAvatar): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(avatar: TutorAvatar)

    @Delete
    suspend fun delete(avatar: TutorAvatar)
}