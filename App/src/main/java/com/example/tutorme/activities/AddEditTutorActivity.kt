package com.example.tutorme.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.arch.BaseMVVMActivity
import com.example.tutorme.R
import com.example.tutorme.constants.IntentConstants
import com.example.tutorme.model.Tutor
import com.example.tutorme.viewmodels.AddEditTutorVM
import kotlinx.android.synthetic.main.activity_add_edit_tutor.*
import kotlinx.android.synthetic.main.content_add_edit_tutor.*
import kotlinx.coroutines.launch
import java.io.File

class AddEditTutorActivity: BaseMVVMActivity<AddEditTutorVM>(AddEditTutorVM::class.java) {

    companion object {

        fun createIntent(context: Context, id: Long?): Intent {
            val intent: Intent = Intent(context, AddEditTutorActivity::class.java)
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

    private val REQUEST_IMAGE_CAPTURE_CODE = 100
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

        id?.let {
            supportActionBar?.title = getString(R.string.title_activity_edit_tutor)
            launch {
                tutor = viewModel.findById(it)
            }.invokeOnCompletion {
                runOnUiThread {
                   fillLayout()
                }
            }
        }?: kotlin.run {
            supportActionBar?.title = getString(R.string.title_activity_add_tutor)
            tutor = Tutor()
        }

    }

    private fun fillLayout(){
        tutor.firstName.let {
            first_name.setText(it)
        }
        tutor.lastName.let {
            last_name.setText(it)
        }
        tutor.city.let {
            place.setText(it)
        }
        tutor.firstName.let {
            first_name.setText(it)
        }
        tutor.pricePerHour.let {
            pricePerHour.setText(String.format("%.0f", it))
        }
        tutor.rating.let {
            stars.setText(String.format("%.1f", it))
        }
        tutor.onlineLecture.let {
            onlineLecture.isChecked = it
        }
        tutor.groupLecture.let {
            groupLecture.isChecked = it
        }
        tutor.homeLecture.let {
            homeLecture.isChecked = it
        }
    }
}