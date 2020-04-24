package com.example.tutorme.sharedpreferences

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager {

    companion object {
        private val fileName = "sptutorme"
        private val firstRun = "first_run"

        // INFO: Vrací objekt k přístupu k SharedPreferences, paratrem je context (který umí zachytit stav)
        private fun getSharedPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences(firstRun, Context.MODE_PRIVATE)
        }




    fun saveFirstRun(context: Context) {
        val editor = getSharedPreferences(context).edit() // INFO: editor vstoupí do SP a umožní editaci
        editor.putBoolean(firstRun, true)
        editor.apply()
    }

    // Info: Vrátí false v případě, že aplikace běží poprvé
    fun isRunForFirstTime(context: Context): Boolean {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.getBoolean(firstRun, true)
    }

    }
}