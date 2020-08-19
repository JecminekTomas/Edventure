package com.example.edventure.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.arch.BaseMVVMActivity
import com.example.edventure.R
import com.example.edventure.constants.IntentConstants
import com.example.edventure.model.Tutor
import com.example.edventure.model.ProfilePicture
import com.example.edventure.utils.FileUtils
import com.example.edventure.utils.PermissionUtil
import com.example.edventure.viewmodels.AddEditTutorVM
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_edit_tutor.*
import kotlinx.android.synthetic.main.content_add_edit_tutor.*
import kotlinx.android.synthetic.main.content_add_edit_tutor.profilePictureIcon
import kotlinx.android.synthetic.main.content_add_edit_tutor.saveChanges
import kotlinx.android.synthetic.main.content_add_edit_tutor.stars_layout
import kotlinx.android.synthetic.main.content_filter_tutor.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.util.*


class AddEditTutorActivity : BaseMVVMActivity<AddEditTutorVM>(AddEditTutorVM::class.java), ChooseImageSourceListener {

    companion object {

        fun createIntent(context: Context, id: Long?): Intent {
            val intent = Intent(context, AddEditTutorActivity::class.java)
            id?.let {
                intent.putExtra(IntentConstants.ID, id)
            }

            return intent
        }
    }

    override val layout: Int = R.layout.activity_add_edit_tutor
    private var id: Long? = null
    private lateinit var tutor: Tutor
    private lateinit var profilePicture: ProfilePicture
    private var tempPhotoFile: File? = null

    private val REQUEST_IMAGE_CAPTURE = 100
    private val GALLERY_IMAGE_REQUEST_CODE = 101

    private val PERMISSION_SELECT_FROM_GALLERY_REQUEST_CODE = 200
    private val PERMISSION_CAMERA_REQUEST_CODE = 201

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        val places: Array<String> = resources.getStringArray(R.array.places_cz)
        val subjects: Array<String> = resources.getStringArray(R.array.subjects_cz)

        val arrayPlacesAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, places)
        add_place_textview.setAdapter(arrayPlacesAdapter)
        val arrayActivitiesAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, subjects)
        add_subject_textview.setAdapter(arrayActivitiesAdapter)

        id?.let {
            supportActionBar?.title = getString(R.string.title_activity_edit_tutor)
            launch {
                tutor = viewModel.findById(it)
            }.invokeOnCompletion {
                runOnUiThread {
                    fillLayout()
                }
            }
        } ?: run {
            supportActionBar?.title = getString(R.string.title_activity_add_tutor)
            tutor = Tutor()
        }
        setInteractionsListener()
    }

    private fun setInteractionsListener() {
        saveChanges.setOnClickListener { saveTutor() }
        profilePictureIcon.setOnClickListener { openAddImageBottomSheet() }
        tutorChangePicture.setOnClickListener { openAddImageBottomSheet()}


        first_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                tutor.firstName = s.toString().trim()
                first_name_layout.error = null
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (first_name.text!!.isNotEmpty() && last_name.text!!.isNotEmpty() && add_place_textview.text!!.isNotEmpty() && price_per_hour.text!!.isNotEmpty() && stars.text!!.isNotEmpty()) {
                    saveChanges.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorPrimary))
                    saveChanges.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
                    saveChanges.isEnabled = true
                }
            }
        })

        last_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                tutor.lastName = s.toString().trim()
                last_name_layout.error = null
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (first_name.text!!.isNotEmpty() && last_name.text!!.isNotEmpty() && add_place_textview.text!!.isNotEmpty() && price_per_hour.text!!.isNotEmpty() && stars.text!!.isNotEmpty()) {
                    saveChanges.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorPrimary))
                    saveChanges.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
                    saveChanges.isEnabled = true
                }
            }
        })

        add_place_textview.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                tutor.city = s.toString().trim()
                add_place_layout.error = null
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (first_name.text!!.isNotEmpty() && last_name.text!!.isNotEmpty() && add_place_textview.text!!.isNotEmpty() && price_per_hour.text!!.isNotEmpty() && stars.text!!.isNotEmpty()) {
                    saveChanges.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorPrimary))
                    saveChanges.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
                    saveChanges.isEnabled = true
                }
            }
        })

        price_per_hour.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                tutor.pricePerHour = s.toString().toDouble()
                price_per_hour_layout.error = null
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (first_name.text!!.isNotEmpty() && last_name.text!!.isNotEmpty() && add_place_textview.text!!.isNotEmpty() && price_per_hour.text!!.isNotEmpty() && stars.text!!.isNotEmpty()) {
                    saveChanges.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorPrimary))
                    saveChanges.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
                    saveChanges.isEnabled = true
                }
            }
        })

        stars.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (first_name.text!!.isNotEmpty() && last_name.text!!.isNotEmpty() && add_place_textview.text!!.isNotEmpty() && price_per_hour.text!!.isNotEmpty() && stars.text!!.isNotEmpty()) {
                    tutor.rating = s.toString().toDouble()
                    stars_layout.error = null
                    saveChanges.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorPrimary))
                    saveChanges.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
                    saveChanges.isEnabled = true
                }
            }
        })
    }

    private fun saveTutor() {
        if (first_name.text!!.isNotEmpty() && last_name.text!!.isNotEmpty() && add_place_textview.text!!.isNotEmpty() && price_per_hour.text!!.isNotEmpty() && stars.text!!.isNotEmpty()) {
            id?.let {
                launch {
                    viewModel.update(tutor)
                }.invokeOnCompletion {
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            } ?: kotlin.run {
                launch {
                    viewModel.insert(tutor)
                    //TODO: Tady bude chyba!
                }.invokeOnCompletion {
                    setResult(Activity.RESULT_OK)
                    finish()
            }
            }
        } else {
            if (first_name.text!!.isEmpty()) {
                first_name_layout.isErrorEnabled = true
                first_name_layout.error = getString(R.string.required_field)
            }
            if (last_name.text!!.isEmpty()) {
                last_name_layout.isErrorEnabled = true
                last_name_layout.error = getString(R.string.required_field)
            }
            if (add_place_textview.text!!.isEmpty()) {
                add_place_layout.error = getString(R.string.required_field)
            }
            if (price_per_hour.text!!.isEmpty()) {
                price_per_hour_layout.isErrorEnabled = true
                price_per_hour_layout.error = getString(R.string.required_field)
            }
            if (stars.text!!.isEmpty()) {
                stars_layout.isErrorEnabled = true
                stars_layout.error = getString(R.string.required_field)
            }
        }
    }

    private fun fillLayout() {
        tutor.firstName.let {
            first_name.setText(it)
        }
        tutor.lastName.let {
            last_name.setText(it)
        }
        tutor.city.let {
            add_place_textview.setText(it)
        }
        tutor.firstName.let {
            first_name.setText(it)
        }
        tutor.pricePerHour.let {
            price_per_hour.setText(String.format("%.0f", it))
        }
        tutor.rating.let {
            stars.setText(String.format("%.1f", it))
        }
    }

    class ChooseImageSourceBottomSheet : BottomSheetDialogFragment() {
        var chooseImageSourceListener: ChooseImageSourceListener? = null

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.dialog_choose_image_source, container, false)

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

            return view
        }
    }


    override fun captureWithCamera() {
        if (PermissionUtil.checkCameraStoragePermission(this)) {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if (takePictureIntent.resolveActivity(packageManager) != null) {
                try {
                    tempPhotoFile = FileUtils.createImageFile(this)
                } catch (ex: IOException) {
                    ex.printStackTrace()
                }
                if (tempPhotoFile != null) {
                    val photoURI = FileProvider.getUriForFile(
                        this,
                        getString(R.string.file_provider_path),
                        tempPhotoFile!!
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        } else {
            PermissionUtil.requestCameraStoragePermission(
                this,
                PERMISSION_CAMERA_REQUEST_CODE
            )
        }

    }

    override fun selectFromGallery() {
        if (PermissionUtil.checkWriteStoragePermission(this)) {
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
            PermissionUtil.requestWriteStoragePermission(
                this@AddEditTutorActivity,
                PERMISSION_SELECT_FROM_GALLERY_REQUEST_CODE
            )
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

    private fun openAddImageBottomSheet() {
        val fragment = ChooseImageSourceBottomSheet()
        fragment.chooseImageSourceListener = this
        fragment.show(supportFragmentManager, "choose_image_source")

    }

    private fun saveImageFile(uri: Uri){
        launch(Dispatchers.Main) {
            val path = FileUtils.getRealPath(this@AddEditTutorActivity, uri)
            val sourceFile = File(path!!)
            val destinationFile = File(filesDir, sourceFile.name)
            try {
                FileUtils.copy(sourceFile, destinationFile)
                tutor.profilePicture = ProfilePicture(Calendar.getInstance().timeInMillis, destinationFile.name)
            } catch (ex: IOException){
                ex.printStackTrace()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            tutor.profilePicture = ProfilePicture(Calendar.getInstance().timeInMillis, tempPhotoFile!!.name)
            showProfilePictureFromFile(tempPhotoFile!!)
        }

        if (requestCode == GALLERY_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            if (data?.data != null){
                val uri = data.data
                uri?.let {
                    saveImageFile(it)
                    showProfilePictureFromUri(uri)
                }
            }
        }
    }

    private fun showProfilePictureFromUri (uri: Uri){
        newProfilePicture.visibility = View.VISIBLE
        profilePictureIcon.visibility = View.INVISIBLE
        Picasso.get().load(uri).into(newProfilePicture)
    }

    private fun showProfilePictureFromFile (file: File){
        newProfilePicture.visibility = View.VISIBLE
        profilePictureIcon.visibility = View.INVISIBLE
        Picasso.get().load(file).into(newProfilePicture)
    }

}

interface ChooseImageSourceListener {
    fun captureWithCamera()
    fun selectFromGallery()
}