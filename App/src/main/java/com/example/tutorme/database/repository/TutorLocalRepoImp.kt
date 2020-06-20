package com.example.tutorme.database.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.tutorme.database.TutorDB
import com.example.tutorme.database.dao.TutorAvatarDao
import com.example.tutorme.database.dao.TutorDao
import com.example.tutorme.model.Tutor

class TutorLocalRepoImp (private val context: Context): ITutorRepository {

    private val tutorDao: TutorDao = TutorDB.getDatabase(context).tutorDao()
    private val tutorAvatarDao: TutorAvatarDao = TutorDB.getDatabase(context).tutorAvatarDao()

    private val getAllLiveData: LiveData<MutableList<Tutor>> = tutorDao.getAll()

    override fun getAll(): LiveData<MutableList<Tutor>> {
        return getAllLiveData
    }

    override suspend fun findById(tutorId: Long): Tutor {
        val tutor = tutorDao.findById(tutorId)
        tutor.avatar = tutorAvatarDao.getAvatar(tutorId)
        return tutor
    }

    override suspend fun findByName(
        firstName: String,
        lastName: String
    ): MutableList<Tutor> {
        val tutors = tutorDao.findByName(firstName, lastName)
        tutors.let {
            for (tutor in it){
                tutor.avatar = tutorAvatarDao.getAvatar(tutor.tutorId)
            }
        }
        return tutors
    }

    override suspend fun findByCity(city: String): MutableList<Tutor> {
        val tutors = tutorDao.findByCity(city)
        tutors.let {
            for (tutor in it){
                tutor.avatar = tutorAvatarDao.getAvatar(tutor.tutorId)
            }
        }
        return tutors
    }

    override suspend fun findByRating(rating: Double): MutableList<Tutor> {
        val tutors = tutorDao.findByRating(rating)
        tutors.let {
            for (tutor in it){
                tutor.avatar = tutorAvatarDao.getAvatar(tutor.tutorId)
            }
        }
        return tutors
    }

    override suspend fun findByPrice(pricePerHour: Double): MutableList<Tutor> {
        val tutors = tutorDao.findByPrice(pricePerHour)
        tutors.let {
            for (tutor in it){
                tutor.avatar = tutorAvatarDao.getAvatar(tutor.tutorId)
            }
        }
        return tutors
    }

    override suspend fun findByOnline(): MutableList<Tutor> {
        val tutors = tutorDao.findByOnline()
        tutors.let {
            for (tutor in it){
                tutor.avatar = tutorAvatarDao.getAvatar(tutor.tutorId)
            }
        }
        return tutors
    }

    override suspend fun findByGroup(): MutableList<Tutor> {
        val tutors = tutorDao.findByGroup()
        tutors.let {
            for (tutor in it){
                tutor.avatar = tutorAvatarDao.getAvatar(tutor.tutorId)
            }
        }
        return tutors
    }

    override suspend fun findByHome(): MutableList<Tutor> {
        val tutors = tutorDao.findByHome()
        tutors.let {
            for (tutor in it){
                tutor.avatar = tutorAvatarDao.getAvatar(tutor.tutorId)
            }
        }
        return tutors
    }

    override suspend fun insert(tutor: Tutor): Long {
        val id = tutorDao.insert(tutor)
        tutor.avatar!!.avatarId = id
        tutorAvatarDao.insert(tutorAvatarDao.getAvatar(id))
        return id
    }

    override suspend fun update(tutor: Tutor) {
        tutorDao.update(tutor)
        if(tutor.avatar!= null){
            tutorAvatarDao.update(tutorAvatarDao.getAvatar(tutor.tutorId))
        }
        else{
            tutorAvatarDao.insert(tutorAvatarDao.getAvatar(tutor.tutorId))
        }
    }

    override suspend fun delete(tutor: Tutor) {
        tutorAvatarDao.delete(tutorAvatarDao.getAvatar(tutor.tutorId))
        tutorDao.delete(tutor)
    }

    //TODO: tutorAvatorDao.getAvatar je velmi zvláštní. Opravit?

}