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
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id"],
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

    @ColumnInfo(name = "user_id")
    var userId: Long = 0
)