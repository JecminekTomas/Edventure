package com.example.tutorme.model

import android.os.Parcel
import android.os.Parcelable
import android.provider.ContactsContract
import android.telephony.PhoneNumberUtils
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "tutor")
data class Tutor(
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "tutor_id")
    var tutorId: Long = 0,

    @ColumnInfo(name = "first_name")
    var firstName: String = "",

    @ColumnInfo(name = "last_name")
    var lastName: String = "",

    @ColumnInfo(name = "city")
    var city: String = "",

    /**@Embedded val city: City */

    //TODO:  Budoucnost - City bude samostatná entita

    @ColumnInfo(name = "phone_number")
    var phoneNumber: String = "",
    /** Pokud bude chtít učitel sdělit telefonní číslo klientovi na jedno kliknutí */

    @ColumnInfo(name = "email")
    var email: String = "",
    /** Kontrolovat při vkládání */

    // TODO: Vymazat ... Cena může být rozdílá u kurzů
    @ColumnInfo(name = "price_per_hour")
    var pricePerHour: Double? = 0.0,

    @ColumnInfo(name = "rating")
    var rating: Double = 0.0,

    @ColumnInfo(name = "online_lecture")
    var onlineLecture: Boolean = false,

    @ColumnInfo(name = "group_lecture")
    var groupLecture: Boolean = false,

    @ColumnInfo(name = "home_lecture")
    var homeLecture: Boolean = false,

    @Ignore
    var avatar: TutorAvatar? = null



    //Zdroj zápisu: Kotlin programming by example.

    /** TODO: Je potřeba vytvořit stupnici od 0 do 5
     ** TODO: V budoucnu - Předělat na mutable list .. aby bylo možno vypočíst průměr hodnocení.

     ** TODO: Nebude to USER, nejedná se o třídu, ale o ENTITU!
     ** TODO: Budoucnost - Bude jenom jednou v RV, ale pokud bude mít rozdílné sazby, stanoví se rozmezí.
     ** TODO: Budoucnost - Pokud bude uživatel vyhledávat dle předmětu, zobrazí se již jenom cena za předmět.
     */

)
