package com.example.tutorme.model

import android.os.Parcel
import android.os.Parcelable
import android.provider.ContactsContract
import android.telephony.PhoneNumberUtils
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// TODO: Tutor bude propojen s USER, což bude nad-entita

@Entity(tableName = "User")
data class Tutor(
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "id_user")
    val id_user: Long? = null,

    @ColumnInfo(name = "first_name")
    val first_name: String? = null,

    @ColumnInfo(name = "last_name")
    val last_name: String? = null,

    @ColumnInfo(name = "city")
    val city: String? = null,

    // TODO: City nebude string, ale bude to výčet českých měst a také jich bude moct být víc
    /** Bude city entita? - Nejspíš ne. Bude potřeba vytvořit nějaké konstanty ze začátku českých měst */

    @ColumnInfo(name = "ranking")
    val ranking: Long? = null,
    // TODO: Předělat na mutable list .. aby bylo možno vypočíst průměr hodnocení.

    @ColumnInfo(name = "phone_number")
    val phone_number: Int? = null,
    /** Pokud bude chtít učitel sdělit telefonní číslo klintovi na jedno kliknutí */

    @ColumnInfo(name = "email")
    val email: String? = null

    // TODO: Předělat na typ e-mail, pokud je.
)
