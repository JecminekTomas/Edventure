package com.example.edventure.model
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "user_id")
    var userId: Long = 0,

    @ColumnInfo(name = "first_name")
    var firstName: String = "",

    @ColumnInfo(name = "last_name")
    var lastName: String = "",

    @ColumnInfo(name = "city")
    var city: String = "",

    @ColumnInfo(name = "phone_number")
    var phoneNumber: String? = "",
    /** Pokud bude chtít učitel sdělit telefonní číslo klientovi na jedno kliknutí */

    @ColumnInfo(name = "email")
    var email: String? = "",
    /** Kontrolovat při vkládání */

    // TODO: Vložit do Teacher
    @ColumnInfo(name = "price_per_hour")
    var pricePerHour: Double? = 0.0,

    @ColumnInfo(name = "rating")
    var rating: Double? = 0.0,

    @Ignore
    var profilePicture: ProfilePicture? = null



    //Zdroj zápisu: Kotlin programming by example.

    /**
     ** TODO: Předělat na mutable list ... aby bylo možno vypočíst průměr hodnocení.
     ** TODO: Bude jenom jednou v RV, ale pokud bude mít rozdílné sazby, stanoví se cena od.
     ** TODO: Pokud bude uživatel vyhledávat dle předmětu, zobrazí se již jenom cena za předmět.
     */

)
