package com.example.tutorme.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "profile_picture",
    foreignKeys =
    [
        ForeignKey(
            entity = Tutor::class,
            parentColumns = ["tutor_id"],
            childColumns = ["profile_picture_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ProfilePicture(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "profile_picture_id")
    var profilePictureId: Long = 0,

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "tutor_id")
    var tutorId: Long = 0
)