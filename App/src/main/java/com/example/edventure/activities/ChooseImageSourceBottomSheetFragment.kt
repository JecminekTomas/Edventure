package com.example.edventure.activities
/*
TODO: Předělat AddEditTeacherFragment
import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.FileProvider
import com.example.edventure.EdventureApplication
import com.example.edventure.R
import com.example.edventure.utils.FileUtils
import com.example.edventure.utils.PermissionUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File
import java.io.IOException

class ChooseImageSourceBottomSheet : BottomSheetDialogFragment(),
    ChooseImageSourceListener  {
    private var tempPhotoFile: File? = null
    private val REQUEST_IMAGE_CAPTURE = 100
    private val GALLERY_IMAGE_REQUEST_CODE = 101
    private val PERMISSION_SELECT_FROM_GALLERY_REQUEST_CODE = 200
    private val PERMISSION_CAMERA_REQUEST_CODE = 201
    var chooseImageSourceListener: ChooseImageSourceListener? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_choose_image_source, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val captureWithCamera: ConstraintLayout = view.findViewById(R.id.captureWithCamera)
        val selectFromGallery: ConstraintLayout = view.findViewById(R.id.selectFromGallery)
        captureWithCamera.setOnClickListener {
            chooseImageSourceListener?.captureWithCamera()
            dismiss()
        }
        selectFromGallery.setOnClickListener {
            chooseImageSourceListener?.selectFromGallery()
            dismiss()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_SELECT_FROM_GALLERY_REQUEST_CODE -> {
                if (PermissionUtil.wasPermissionGranted(grantResults)) {
                    selectFromGallery()
                }
            }
            PERMISSION_CAMERA_REQUEST_CODE -> {
                if (PermissionUtil.wasPermissionGranted(grantResults)) {
                    captureWithCamera()
                }
            }
        }
    }

    override fun captureWithCamera() {
        if (PermissionUtil.checkCameraStoragePermission(EdventureApplication.appContext)) {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePictureIntent, 100)
            try {
                tempPhotoFile = FileUtils.createImageFile(EdventureApplication.appContext)
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
            if (tempPhotoFile != null) {
                val photoURI = FileProvider.getUriForFile(
                    EdventureApplication.appContext,
                    getString(R.string.file_provider_path),
                    tempPhotoFile!!
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        } else {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), PERMISSION_CAMERA_REQUEST_CODE)
        }
    }

    override fun selectFromGallery() {
        if (PermissionUtil.checkWriteStoragePermission(EdventureApplication.appContext)) {
            val intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(
                    intent,
                    getString(R.string.select_image)
                ), GALLERY_IMAGE_REQUEST_CODE
            )
        } else {
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_SELECT_FROM_GALLERY_REQUEST_CODE)
        }
    }

}
 */