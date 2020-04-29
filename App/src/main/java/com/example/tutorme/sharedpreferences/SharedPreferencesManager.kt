package com.example.tutorme.sharedpreferences

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager {

    companion object {
        private const val fileName = "sptutorme"
        private const val firstRun = "first_run"

        // INFO: Vrací objekt k přístupu k SharedPreferences, paratrem je context (který umí zachytit stav)
        private fun getSharedPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        }




    fun saveFirstRun(context: Context) {
        val editor = getSharedPreferences(context).edit() // INFO: editor vstoupí do SP a umožní editaci
        editor.putBoolean(firstRun, false) // INFO: POZOR - Musím nastavit hodnotu na false. (logicky - neběží poprvé)
        editor.apply()
    }

    // Info: Vrátí false v případě, že aplikace běží poprvé
    fun isRunForFirstTime(context: Context): Boolean {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.getBoolean(firstRun, true)
    }

    }
}