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
import com.example.arch.activities.BaseMVVMActivity
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
import kotlinx.android.synthetic.main.content_add_edit_tutor.saveTutor
import kotlinx.android.synthetic.main.content_add_edit_tutor.rating_layout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.util.*


class AddEditTutorActivity : BaseMVVMActivity<AddEditTutorVM>(AddEditTutorVM::class.java)
   {
       override val layout: Int = R.layout.activity_add_edit_tutor
   }
    /*
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

        loadArrayStrings()

        if (intent.hasExtra(IntentConstants.ID)) {
            id = intent.getLongExtra(IntentConstants.ID, -1)
        }

        id?.let {
            supportActionBar?.title = getString(R.string.title_activity_edit_tutor)
            launch(Dispatchers.Main) {
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
        saveTutor.setOnClickListener { saveTutor() }
        profilePictureIcon.setOnClickListener { openAddImageBottomSheet() }
        tutorChangePicture.setOnClickListener { openAddImageBottomSheet() }


        first_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                tutor.firstName = s.toString().trim()
                first_name_layout.error = null
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isFilled()) {
                    saveTutorEnabled()
                } else {
                    saveTutorDisabled()
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
                if (isFilled()) {
                    saveTutorEnabled()
                } else {
                    saveTutorDisabled()
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
                if (isFilled()) {
                    saveTutorEnabled()
                } else {
                    saveTutorDisabled()
                }
            }
        })

        price_per_hour.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tutor.pricePerHour = s.toString().toDouble()
                price_per_hour_layout.error = null
                if (isFilled()) {
                    if (tutor.pricePerHour!! in 0.0..1000.0) {
                        saveTutorEnabled()
                    } else {
                        price_per_hour_layout.isErrorEnabled = true
                        if (tutor.pricePerHour!! < 0) {
                            price_per_hour_layout.error = getString(R.string.not_possible)
                        } else {
                            price_per_hour_layout.error =
                                getString(R.string.student_should_need_loan_for_your_services)
                        }
                    }

                } else {
                    saveTutorDisabled()
                }
            }
        })

        stars.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isFilled()) {
                    tutor.rating = s.toString().toDouble()
                    if (tutor.rating in 0.0..5.0) {
                        rating_layout.error = null
                        saveTutorEnabled()
                    } else {
                        rating_layout.isErrorEnabled = true
                        rating_layout.error = getString(R.string.required_field)
                    }
                } else {
                    saveTutorDisabled()
                }
            }
        })
    }

    private fun saveTutor() {
        if (isFilled()) {
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
                }.invokeOnCompletion {
                    setResult(Activity.RESULT_OK)
                    finish()
                }
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

    private fun isFilled(): Boolean {
        return first_name.text!!.isNotEmpty() && last_name.text!!.isNotEmpty() && add_place_textview.text!!.isNotEmpty() && price_per_hour.text!!.isNotEmpty() && stars.text!!.isNotEmpty() && tutor.profilePicture != null
    }

    private fun saveTutorEnabled() {
        saveTutor.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.colorPrimary
            )
        )
        saveTutor.setTextColor(
            ContextCompat.getColor(
                this,
                R.color.white
            )
        )
        saveTutor.isEnabled = true
    }

    private fun saveTutorDisabled() {
        saveTutor.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.colorSecondary
            )
        )
        saveTutor.isEnabled = false
    }

    private fun loadArrayStrings() {
        val arrayPlacesAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            resources.getStringArray(R.array.places_cz)
        )
        val arrayActivitiesAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            resources.getStringArray(R.array.subjects_cz)
        )

        add_place_textview.setAdapter(arrayPlacesAdapter)
        add_subject_textview.setAdapter(arrayActivitiesAdapter)
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

    private fun saveImageFile(uri: Uri) {
        launch(Dispatchers.Main) {
            val path = FileUtils.getRealPath(this@AddEditTutorActivity, uri)
            val sourceFile = File(path)
            val destinationFile = File(filesDir, sourceFile.name)
            try {
                FileUtils.copy(sourceFile, destinationFile)
                tutor.profilePicture =
                    ProfilePicture(Calendar.getInstance().timeInMillis, destinationFile.name)
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            tutor.profilePicture =
                ProfilePicture(Calendar.getInstance().timeInMillis, tempPhotoFile!!.name)
            showProfilePictureFromFile(tempPhotoFile!!)
        }

        if (requestCode == GALLERY_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data?.clipData != null) {
                val count = data.clipData?.itemCount
                (0 until count!!).forEach { i ->
                    val imageUri: Uri = data.clipData!!.getItemAt(i).uri
                    saveImageFile(imageUri)
                    showProfilePictureFromUri(imageUri)
                }
            } else if (data?.data != null) {
                val uri = data.data
                uri?.let {
                    saveImageFile(uri)
                    showProfilePictureFromUri(uri)
                }
            }
        }
    }

    private fun showProfilePictureFromUri(uri: Uri) {
        newProfilePicture.visibility = View.VISIBLE
        profilePictureIcon.visibility = View.INVISIBLE
        Picasso.get().load(uri).into(newProfilePicture)
    }

    private fun showProfilePictureFromFile(file: File) {
        newProfilePicture.visibility = View.VISIBLE
        profilePictureIcon.visibility = View.INVISIBLE
        Picasso.get()
            .load(file)
            .placeholder(R.drawable.ic_custom_profile_secondary_dark_24)
            .error(R.drawable.ic_custom_profile_secondary_dark_24)
            .centerCrop()
            .fit()
            .into(newProfilePicture)
    }
*/
}


/*
interface ChooseImageSourceListener {
    fun captureWithCamera()
    fun selectFromGallery()
}*/