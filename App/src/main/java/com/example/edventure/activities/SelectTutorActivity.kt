package com.example.edventure.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.arch.BaseMVVMActivity
import com.example.edventure.R
import com.example.edventure.model.Tutor
import com.example.edventure.sharedpreferences.SharedPreferencesManager
import com.example.edventure.viewmodels.SelectTutorVM
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_select_tutor.*
import kotlinx.android.synthetic.main.content_select_tutor.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig
import java.io.File
import java.util.*


class SelectTutorActivity : BaseMVVMActivity<SelectTutorVM>(SelectTutorVM::class.java) {

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, SelectTutorActivity::class.java)
        }
    }

    override val layout: Int = R.layout.activity_select_tutor
    private var tutorList: MutableList<Tutor> = mutableListOf()
    private var saveTutorList: MutableList<Tutor> = mutableListOf()
    private var mExpandedPosition = -1
    private var previousExpandedPosition = -1
    private var filtered: Boolean = false

    var filterRating: Double? = -1.0
    var filterPriceMin: Double? = -1.0
    var filterPriceMax: Double? = -1.0
    var filterPlace: String? = ""

    private val FILTER_TUTOR_REQUEST = 100

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var tutorAdapter: TutorAdapter

    /**  layoutManager se stará o pozicování - Existuje možnost i GridManageru, atd. */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        tutorAdapter = TutorAdapter()
        layoutManager = LinearLayoutManager(this)
        selectTutorRecyclerView.layoutManager = layoutManager
        selectTutorRecyclerView.adapter = tutorAdapter

        viewModel.getAll().observe(this, object : Observer<MutableList<Tutor>> {
            override fun onChanged(t: MutableList<Tutor>?) {
                t?.let {
                    val diffUtil = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                        override fun areItemsTheSame(
                            oldItemPosition: Int,
                            newItemPosition: Int
                        ): Boolean {
                            return tutorList[oldItemPosition].tutorId == t[newItemPosition].tutorId
                        }

                        override fun areContentsTheSame(
                            oldItemPosition: Int,
                            newItemPosition: Int
                        ): Boolean {
                            return tutorList[oldItemPosition] == t[newItemPosition]
                        }

                        override fun getOldListSize() = tutorList.size

                        override fun getNewListSize() = t.size
                    })
                    diffUtil.dispatchUpdatesTo(tutorAdapter)
                    tutorList.clear()
                    tutorList.addAll(it)

                }
                /** DiffUtil je velice užitečné využívat, jelikož může velice zrychlit proces při načítání změny RV.*/
            }
        })
        if (SharedPreferencesManager.isRunForFirstTime(this)) {
            setHelp()
            SharedPreferencesManager.saveFirstRun(this)
        }
    }


    /** Metoda onOptionSelected slouží při kliknutí na položku v horní liště (filtrování a hledání) */

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_tutor -> {
                onActionAddTutor()
                return true
            }
            R.id.action_filter -> {
                onActionFilter()
                return true
            }
            R.id.action_search -> {
                onActionSearch()
                return true
            }
            R.id.action_cancel_filter -> {
                onActionCancelFilter()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onActionCancelFilter() {
        tutorList = saveTutorList
        selectTutorRecyclerView.adapter = tutorAdapter
        filtered = false
        invalidateOptionsMenu()
    }

    private fun onActionAddTutor() {
        startActivity(AddEditTutorActivity.createIntent(this, null))
    }

    private fun onActionFilter() {
        startActivityForResult(FilterTutorActivity.createIntent(this), FILTER_TUTOR_REQUEST)
    }

    private fun onActionSearch() {
        // TODO: Vytvořit SearchTutorActivity
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.clear()
        if (!filtered) {
            menuInflater.inflate(R.menu.menu_select_tutor, menu)
        } else {
            menuInflater.inflate(R.menu.menu_filter_tutor, menu)
        }
        return true
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILTER_TUTOR_REQUEST && resultCode == Activity.RESULT_OK) {
            saveTutorList = tutorList.toMutableList()
            when (data?.getStringExtra("filter_type")) {
                "filter_rating" -> {
                    filterRating = data.getDoubleExtra("filter_rating", -1.0)
                    tutorList.removeAll { it.rating < filterRating!! }
                }
                "filter_place" -> {
                    filterPlace = data.getStringExtra("filter_place")
                    tutorList.removeAll { it.city != filterPlace }
                }
                "filter_price_min" -> {
                    filterPriceMin = data.getDoubleExtra("filter_price_min", -1.0)
                    tutorList.removeAll { it.pricePerHour!! <= filterPriceMin!! }
                }
                "filter_price_max" -> {
                    filterPriceMax = data.getDoubleExtra("filter_price_max", -1.0)
                    tutorList.removeAll { it.pricePerHour!! >= filterPriceMax!! }
                }
            }
            filtered = true
            selectTutorRecyclerView.adapter = tutorAdapter
            invalidateOptionsMenu()
        }
    }

    private fun setHelp() {
        val config = ShowcaseConfig()
        val navHome = findViewById<BottomNavigationItemView>(R.id.navigation_home)
        val navSearch = findViewById<BottomNavigationItemView>(R.id.navigation_search)
        val navChat = findViewById<BottomNavigationItemView>(R.id.navigation_chat)
        val navCalendar = findViewById<BottomNavigationItemView>(R.id.navigation_calendar)
        val navProfile = findViewById<BottomNavigationItemView>(R.id.navigation_profile)
        config.delay = 500 // half second between each showcase view
        config.maskColor = R.color.colorPrimary


        val sequence = MaterialShowcaseSequence(this, "Show me app")

        sequence.setConfig(config)

        sequence.addSequenceItem(
            navHome,
            "Here you can see...", "GOT IT"
        )

        sequence.addSequenceItem(
            navSearch,
            "Here you can see...", "GOT IT"
        )

        sequence.addSequenceItem(
            navChat,
            "Here you can see...", "GOT IT"
        )

        sequence.addSequenceItem(
            navCalendar,
            "Here you can see...", "GOT IT"
        )

        sequence.addSequenceItem(
            navProfile,
            "Here you can see...", "GOT IT"
        )

        sequence.start()
    }


    inner class TutorAdapter : RecyclerView.Adapter<TutorAdapter.TutorViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_select_tutor, parent, false)
            return TutorViewHolder(view)
        }

        override fun getItemCount() = tutorList.size

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: TutorViewHolder, position: Int) {
            val isExpanded = position == mExpandedPosition
            holder.buttons.visibility = if (isExpanded) View.VISIBLE else View.GONE
            holder.tutorCardView.isActivated = isExpanded

            if (isExpanded)
                previousExpandedPosition = position

            holder.tutorCardView.setOnClickListener {
                mExpandedPosition = if (isExpanded) -1 else position
                notifyItemChanged(previousExpandedPosition)
                notifyItemChanged(position)
            }

            holder.buttonDelete.setOnClickListener {
                launch {
                    viewModel.delete(tutorList[holder.adapterPosition])
                }
            }

            holder.buttonProfile.setOnClickListener {
                startActivity(
                    TutorProfileActivity.createIntent(
                        applicationContext,
                        tutorList[holder.adapterPosition].tutorId
                    )
                )
            }

            val tutor = tutorList[position]
            launch(Dispatchers.Main) {
                tutor.profilePicture = viewModel.findProfilePicture(tutor.tutorId)
                Picasso.get().load(File(filesDir, tutor.profilePicture!!.name)).noFade()
                    .into(holder.tutorProfilePicture)
            }.invokeOnCompletion {
                runOnUiThread {

                    holder.tutorName.text = "${tutor.firstName} ${tutor.lastName}"
                    holder.tutorCity.text = tutor.city
                    holder.tutorPrice.text = String.format(
                        "%.0f Kč/h",
                        tutor.pricePerHour
                    ) //TODO: Kč/h změnit na tutor.mena -- v BUDOUCNU.
                    holder.tutorRating.text = String.format(Locale.US, "★ %.1f", tutor.rating)
                }
            }

        }


        /** ViewHolder slouží pro organizaconizaci požadavků na VIEW od jednotlivých elementů.*/
        inner class TutorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tutorProfilePicture: CircleImageView =
                view.findViewById(R.id.profilePictureIconSelect)
            val tutorName: TextView = view.findViewById(R.id.tutorName)
            val tutorCity: TextView = view.findViewById(R.id.tutorCity)
            val tutorPrice: TextView = view.findViewById(R.id.tutorPrice)
            val tutorRating: TextView = view.findViewById(R.id.tutorRating)
            val buttonProfile: Button = view.findViewById(R.id.buttonProfile)
            val buttonDelete: Button = view.findViewById(R.id.buttonDelete)
            val buttons: LinearLayout = view.findViewById(R.id.card_view_buttons)
            val tutorCardView: CardView = view.findViewById(R.id.tutor_card_view)
        }
    }
}


