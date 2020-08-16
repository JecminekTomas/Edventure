package com.example.edventure.utils

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionUtil {

    companion object {

        fun requestWriteStoragePermission(context: AppCompatActivity, requestCode: Int) =
            requestPermissions(context, requestCode, WRITE_EXTERNAL_STORAGE)

        fun checkWriteStoragePermission(context: AppCompatActivity): Boolean
                = checkPermissions(context, WRITE_EXTERNAL_STORAGE)

        fun requestCameraStoragePermission(context: AppCompatActivity, requestCode: Int)
                = requestPermissions(context, requestCode, android.Manifest.permission.CAMERA)

        fun checkCameraStoragePermission(context: AppCompatActivity): Boolean
                = checkPermissions(context, android.Manifest.permission.CAMERA)

        fun wasPermissionGranted(grantResults: IntArray): Boolean
                = grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED

        private fun checkPermissions(context: AppCompatActivity, permission: String): Boolean {
            return ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }

        private fun requestPermissions(context: Activity, requestCode: Int, permission: String) {
            ActivityCompat.requestPermissions(
                context,
                arrayOf(permission),
                requestCode
            )
        }
    }


}