package com.example.tutorme.utils

import android.content.Context
import java.io.File

object ImageUtils {

    fun deleteImageFile(context: Context, fileName: String){
        val fileToDelete = File(context.filesDir, fileName)
        if (fileToDelete.exists()) {
            fileToDelete.delete()
        }
    }
}