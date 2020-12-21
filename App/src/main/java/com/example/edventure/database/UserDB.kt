package com.example.edventure.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.edventure.database.dao.ProfilePictureDao
import com.example.edventure.database.dao.UserDao
import com.example.edventure.model.User
import com.example.edventure.model.ProfilePicture

@Database(entities = [User::class, ProfilePicture::class], version = 1, exportSchema = false)
abstract class UserDB : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun profilePictureDao(): ProfilePictureDao

    companion object {
        private var INSTANCE: UserDB? = null

        fun getDatabase(context: Context): UserDB{
            if (INSTANCE == null){
                synchronized(UserDB::class.java){ /** synchronized znamená, že se bude provádět pouze toto vlákno v jeden moment*/
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            UserDB::class.java, "user_db"
                        )   .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}