package com.example.edventure.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.arch.BaseMVVMActivity
import com.example.edventure.R
import com.example.edventure.constants.IntentConstants
import com.example.edventure.model.Tutor
import com.example.edventure.viewmodels.TutorProfileVM
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_tutor_profile.*
import kotlinx.android.synthetic.main.content_tutor_profile.*
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

class TutorProfileActivity : BaseMVVMActivity<TutorProfileVM>(TutorProfileVM::class.java) {
    companion object {
        fun createIntent(context: Context, id: Long): Intent {
            val intent = Intent(context, TutorProfileActivity::class.java)
            intent.putExtra(IntentConstants.ID, id)
            return intent
        }
    }

    override val layout: Int = R.layout.activity_tutor_profile
    lateinit var tutor: Tutor
    private var id: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
        id = intent.getLongExtra(IntentConstants.ID, -1)
        getTask()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_tutor_profile, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit_tutor -> {
                onActionEditTutor()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onActionEditTutor() {
        startActivity(AddEditTutorActivity.createIntent(this, id))
    }

    private fun getTask() {
        launch {
            tutor = viewModel.findById(id)
        }.invokeOnCompletion {
            runOnUiThread {
                fillLayout()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun fillLayout() {
        Picasso.get().load(File(filesDir, tutor.profilePicture!!.name))
            .placeholder(R.drawable.ic_custom_profile_secondary_dark_24)
            .error(R.drawable.ic_custom_profile_secondary_dark_24)
            .centerCrop()
            .fit()
            .into(profile_picture)
        profile_name.text = "${tutor.firstName} ${tutor.lastName}"
        profile_city.text = tutor.city
        profile_rating.text = String.format(Locale.US, "★ %.1f", tutor.rating)
        profile_enrolled_now.text = "${(0..20).random()}"
        profile_enrolled_all.text = "${(0..100).random()}"
        profile_average_time.text = "${(1..5).random()}h"
    }
}