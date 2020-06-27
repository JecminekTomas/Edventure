package com.example.tutorme.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "avatar",
    foreignKeys =
    [
        ForeignKey(
            entity = Tutor::class,
            parentColumns = ["tutor_id"],
            childColumns = ["avatar_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TutorAvatar(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "avatar_id")
    var avatarId: Long = 0,

    @ColumnInfo(name = "name")
    var avatarName: String = ""
)