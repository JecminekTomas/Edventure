package com.example.edventure.model
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

    //TODO:  Budoucnost - City bude nalezeno v DB

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

    @Ignore
    var profilePicture: ProfilePicture? = null



    //Zdroj zápisu: Kotlin programming by example.

    /** TODO: Je potřeba vytvořit stupnici od 0 do 5
     ** TODO: V budoucnu - Předělat na mutable list .. aby bylo možno vypočíst průměr hodnocení.
     ** TODO: Budoucnost - Bude jenom jednou v RV, ale pokud bude mít rozdílné sazby, stanoví se cena od.
     ** TODO: Budoucnost - Pokud bude uživatel vyhledávat dle předmětu, zobrazí se již jenom cena za předmět.
     */

)
