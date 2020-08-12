package com.example.tutorme.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.tutorme.database.dao.ProfilePictureDao
import com.example.tutorme.database.dao.TutorDao
import com.example.tutorme.model.Tutor
import com.example.tutorme.model.ProfilePicture

@Database(entities = [Tutor::class, ProfilePicture::class], version = 2, exportSchema = false)
abstract class TutorDB : RoomDatabase() {
    abstract fun tutorDao(): TutorDao
    abstract fun profilePictureDao(): ProfilePictureDao

    companion object {
        private var INSTANCE: TutorDB? = null

        fun getDatabase(context: Context): TutorDB{
            if (INSTANCE == null){
                synchronized(TutorDB::class.java){ /** synchronized znamená, že se bude provádět pouze toto vlákno v jeden moment*/
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            TutorDB::class.java, "tutor_db"
                        )   .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}