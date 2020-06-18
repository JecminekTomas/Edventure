package com.example.tutorme.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tutorme.database.dao.TutorAvatarDao
import com.example.tutorme.database.dao.TutorDao
import com.example.tutorme.model.Tutor
import com.example.tutorme.model.TutorAvatar

@Database(entities = [Tutor::class, TutorAvatar::class], version = 1, exportSchema = true)
abstract class TutorDB : RoomDatabase() {
    abstract fun tutorDao(): TutorDao
    abstract fun tutorAvatarDao(): TutorAvatarDao

    companion object {
        private var INSTANCE: TutorDB? = null

        fun getDatabase(context: Context): TutorDB{
            if (INSTANCE == null){
                synchronized(TutorDB::class.java){ /** synchronized znamená, že se bude provádět pouze toto vlákno v jeden moment*/
                   INSTANCE = Room.databaseBuilder(
                       context.applicationContext,
                       TutorDB::class.java, "tutor_db")
                       .fallbackToDestructiveMigration()
                       .build()
                }
            }
            return INSTANCE!!
        }
    }
}