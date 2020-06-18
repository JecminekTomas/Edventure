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
    val tutorId: Long = 0,

    @ColumnInfo(name = "first_name")
    val firstName: String = "",

    @ColumnInfo(name = "last_name")
    val lastName: String = "",

    @ColumnInfo(name = "city")
    val city: String = "",

    /**@Embedded val city: City */

    //TODO:  Budoucnost - City bude samostatná entita

    @ColumnInfo(name = "phone_number")
    val phoneNumber: String = "",
    /** Pokud bude chtít učitel sdělit telefonní číslo klientovi na jedno kliknutí */

    @ColumnInfo(name = "email")
    val email: String = "",
    /** Kontrolovat při vkládání */

    // TODO: Vymazat ... Cena může být rozdílá u kurzů
    @ColumnInfo(name = "price_per_hour")
    val pricePerHour: Double = 0.0,

    @ColumnInfo(name = "rating")
    val rating: Double = 0.0,

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
