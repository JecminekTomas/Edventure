package com.example.edventure.model

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
            childColumns = ["tutor_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
class ProfilePicture(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "profile_picture_id")
    var profilePictureId: Long = 0,

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "tutor_id")
    var tutorId: Long = 0
)