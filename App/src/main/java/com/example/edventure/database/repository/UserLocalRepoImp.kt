package com.example.edventure.database.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.edventure.database.UserDB
import com.example.edventure.database.dao.ProfilePictureDao
import com.example.edventure.database.dao.UserDao
import com.example.edventure.model.ProfilePicture
import com.example.edventure.model.User

class UserLocalRepoImp(context: Context) : IUserRepository {

    private val userDao: UserDao = UserDB.getDatabase(context).userDao()
    private val profilePictureDao: ProfilePictureDao =
        UserDB.getDatabase(context).profilePictureDao()

    private val getAllLiveData: LiveData<MutableList<User>> = userDao.getAll()

    override fun getAll(): LiveData<MutableList<User>> {
        return getAllLiveData
    }

    override fun findCities(): MutableList<String> {
        return userDao.findCities()
    }

    override suspend fun findById(userId: Long): User {
        val user = userDao.findById(userId)
        user.profilePicture = profilePictureDao.getProfilePicture(userId)
        return user
    }

    override suspend fun findProfilePicture(userId: Long): ProfilePicture {
        return profilePictureDao.getProfilePicture(userId)
    }

    override suspend fun findByName(
        firstName: String,
        lastName: String
    ): MutableList<User> {
        val users = userDao.findByName(firstName, lastName)
        users.let {
            for (user in it) {
                user.profilePicture = profilePictureDao.getProfilePicture(user.userId)
            }
        }
        return users
    }

    override suspend fun findByCity(city: String): MutableList<User> {
        val users = userDao.findByCity(city)
        users.let {
            for (user in it) {
                user.profilePicture = profilePictureDao.getProfilePicture(user.userId)
            }
        }
        return users
    }

    override suspend fun findByRating(rating: Double): MutableList<User> {
        val users = userDao.findByRating(rating)
        users.let {
            for (user in it) {
                user.profilePicture = profilePictureDao.getProfilePicture(user.userId)
            }
        }
        return users
    }

    override suspend fun findByPriceLowerThan(pricePerHour: Double): MutableList<User> {
        val users = userDao.findByPriceLowerThan(pricePerHour)
        users.let {
            for (user in it) {
                user.profilePicture = profilePictureDao.getProfilePicture(user.userId)
            }
        }
        return users
    }

    override suspend fun findByPriceHigherThan(pricePerHour: Double): MutableList<User> {
        val users = userDao.findByPriceLowerThan(pricePerHour)
        users.let {
            for (user in it) {
                user.profilePicture = profilePictureDao.getProfilePicture(user.userId)
            }
        }
        //TODO: Změnit .. udělat průnik dvou seznamů.
        return users
    }

    override suspend fun insert(user: User): Long {
        val id = userDao.insert(user)
        val profilePicture = user.profilePicture
        if (profilePicture != null) {
            profilePicture.userId = id
            profilePictureDao.insert(profilePicture)
        }
        return id
    }

    override suspend fun update(user: User) {
        userDao.update(user)
        val profilePicture = user.profilePicture
        if (profilePicture != null) {
            profilePictureDao.update(profilePicture)
        }
    }

    override suspend fun delete(user: User) {
        val profilePicture = user.profilePicture
        if (profilePicture != null) {
            profilePictureDao.delete(profilePicture)
        }
        userDao.delete(user)
    }
}