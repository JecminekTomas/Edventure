package com.example.tutorme.model

import android.os.Parcel
import android.os.Parcelable
import android.provider.ContactsContract
import android.telephony.PhoneNumberUtils
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// TODO: Tutor bude propojen s USER, což bude nad-entita

@Entity(tableName = "tutor")
data class Tutor(
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "tutorId")
    val tutorId: Long? = null,

    @ColumnInfo(name = "firstName")
    val firstName: String? = null,

    @ColumnInfo(name = "lastName")
    val lastName: String? = null,

    @ColumnInfo(name = "city")
    val city: String? = null,

    // TODO: Bude city v DB? ... Neexistuje jiná kontrola města?
    // TODO: City nebude string, ale bude to výčet českých měst a také jich bude moct být víc.. b
    /** Bude city entita? - Nejspíš ne. Bude potřeba vytvořit nějaké konstanty ze začátku českých měst */

    @ColumnInfo(name = "phoneNumber")
    val phoneNumber: String? = null,
    /** Pokud bude chtít učitel sdělit telefonní číslo klintovi na jedno kliknutí */

    @ColumnInfo(name = "email")
    val email: String? = null,
    // TODO: Předělat na typ e-mail, pokud je.

    // TODO: Vymazat ... Cena může být rozdílá u kurzů
    @ColumnInfo(name = "pricePerHour")
    val pricePerHour: Double? = null,

    @ColumnInfo(name = "rating")
    val rating: Double? = null


    /** TODO: Je potřeba vytvořit stupnici od 0 do 5
     ** TODO: Předělat na mutable list .. aby bylo možno vypočíst průměr hodnocení. .. Nebo NE.

     ** TODO: Nebude to USER, nejedná se o třídu, ale o ENTITU!
     ** TODO: Bude jenom jednou v RV, ale pokud bude mít rozdílné sazby, stanoví se rozmezí.
     ** TODO: Pokud bude uživatel vyhledávat dle předmětu, zobrazí se již jenom cena za předmět.
     */

)
