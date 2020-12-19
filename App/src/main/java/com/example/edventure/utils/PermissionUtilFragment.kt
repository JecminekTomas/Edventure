package com.example.edventure.utils


import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.checkSelfPermission
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.edventure.EdventureApplication.Companion.appContext

class PermissionUtilFragment {

    companion object {

        fun requestWriteStoragePermission(requestCode: Int) =
            requestPermission(requestCode, WRITE_EXTERNAL_STORAGE)

        fun checkWriteStoragePermission(): Boolean
                = checkPermissions(WRITE_EXTERNAL_STORAGE)

        fun requestCameraStoragePermission(requestCode: Int)
                = requestPermission(requestCode, android.Manifest.permission.CAMERA)

        fun checkCameraStoragePermission(): Boolean
                = checkPermissions(android.Manifest.permission.CAMERA)

        fun wasPermissionGranted(grantResults: IntArray): Boolean
                = grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED

        private fun checkPermissions(permission: String): Boolean {
            return ContextCompat.checkSelfPermission(
                appContext,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }

        private fun requestPermission(requestCode: Int, permission: String) {
            requestPermissions(
                appContext as Activity,
                arrayOf(permission),
                requestCode
            )
        }
    }


}