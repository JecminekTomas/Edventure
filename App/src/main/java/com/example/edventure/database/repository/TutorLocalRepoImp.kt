package com.example.edventure.database.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.edventure.database.TutorDB
import com.example.edventure.database.dao.ProfilePictureDao
import com.example.edventure.database.dao.TutorDao
import com.example.edventure.model.Tutor

class TutorLocalRepoImp (context: Context): ITutorRepository {

    private val tutorDao: TutorDao = TutorDB.getDatabase(context).tutorDao()
    private val profilePictureDao: ProfilePictureDao = TutorDB.getDatabase(context).profilePictureDao()

    private val getAllLiveData: LiveData<MutableList<Tutor>> = tutorDao.getAll()

    override fun getAll(): LiveData<MutableList<Tutor>> {
        return getAllLiveData
    }

    override fun findCities(): MutableList<String> {
        return tutorDao.findCities()
    }

    override suspend fun findById(tutorId: Long): Tutor {
        val tutor = tutorDao.findById(tutorId)
        tutor.profilePicture = profilePictureDao.getProfilePicture(tutorId)
        return tutor
    }

    override suspend fun findByName(
        firstName: String,
        lastName: String
    ): MutableList<Tutor> {
        val tutors = tutorDao.findByName(firstName, lastName)
        tutors.let {
            for (tutor in it){
                tutor.profilePicture = profilePictureDao.getProfilePicture(tutor.tutorId)
            }
        }
        return tutors
    }

    override suspend fun findByCity(city: String): MutableList<Tutor> {
        val tutors = tutorDao.findByCity(city)
        tutors.let {
            for (tutor in it){
                tutor.profilePicture = profilePictureDao.getProfilePicture(tutor.tutorId)
            }
        }
        return tutors
    }

    override suspend fun findByRating(rating: Double): MutableList<Tutor> {
        val tutors = tutorDao.findByRating(rating)
        tutors.let {
            for (tutor in it){
                tutor.profilePicture = profilePictureDao.getProfilePicture(tutor.tutorId)
            }
        }
        return tutors
    }

    override suspend fun findByPrice(pricePerHour: Double): MutableList<Tutor> {
        val tutors = tutorDao.findByPrice(pricePerHour)
        tutors.let {
            for (tutor in it){
                tutor.profilePicture = profilePictureDao.getProfilePicture(tutor.tutorId)
            }
        }
        return tutors
    }

    override suspend fun insert(tutor: Tutor): Long {
        val id = tutorDao.insert(tutor)
        val profilePicture = tutor.profilePicture
        if (profilePicture != null){
            profilePicture.tutorId = id
            profilePictureDao.insert(profilePicture)
        }
        return id
    }

    override suspend fun update(tutor: Tutor) {
        tutorDao.update(tutor)
        val profilePicture = tutor.profilePicture
        if (profilePicture != null) {
            profilePictureDao.update(profilePicture)
        }
    }

    override suspend fun delete(tutor: Tutor) {
        val profilePicture = tutor.profilePicture
        if (profilePicture != null) {
            profilePictureDao.delete(profilePicture)
        }
        tutorDao.delete(tutor)
    }

    //TODO: tutorAvatorDao.getAvatar je velmi zvláštní. Opravit?

}