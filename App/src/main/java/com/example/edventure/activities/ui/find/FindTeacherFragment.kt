package com.example.edventure.activities.ui.find

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.arch.fragments.BaseMVVMFragment
import com.example.edventure.R
import com.example.edventure.EdventureApplication.Companion.appContext
import com.example.edventure.model.User
import com.example.edventure.sharedpreferences.SharedPreferencesManager
import com.example.edventure.viewmodels.SelectUserVM
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_find_teacher.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

class FindTeacherFragment : BaseMVVMFragment<SelectUserVM>(SelectUserVM::class.java) {

    override val layout: Int = R.layout.fragment_find_teacher
    private var teachersList: MutableList<User> = mutableListOf()
    private var savedTeachersList: MutableList<User> = mutableListOf()
    private var mExpandedPosition = -1
    private var previousExpandedPosition = -1
    private var filtered: Boolean = false

    var filterRating: Double? = -1.0
    var filterPriceMin: Double? = -1.0
    var filterPriceMax: Double? = -1.0
    var filterPlace: String? = ""

    private val FILTER_TEACHER_REQUEST = 100
    private lateinit var teachersAdapter: TeachersAdapter

    /**  V onCreateView vždy pouze "inflatovat" layout */

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val navController = findNavController()

        /*navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("key")?.observe(
            viewLifecycleOwner) { result ->

        }*/

        findTeacherRecyclerView.layoutManager = LinearLayoutManager(activity)
        findTeacherRecyclerView.adapter = teachersAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        teachersAdapter = TeachersAdapter()
        viewModel.getAll().observe(this, object : Observer<MutableList<User>> {
            override fun onChanged(t: MutableList<User>?) {
                t?.let {
                    val diffUtil = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                        override fun areItemsTheSame(
                            oldItemPosition: Int,
                            newItemPosition: Int
                        ): Boolean {
                            return teachersList[oldItemPosition].userId == t[newItemPosition].userId
                        }

                        override fun areContentsTheSame(
                            oldItemPosition: Int,
                            newItemPosition: Int
                        ): Boolean {
                            return teachersList[oldItemPosition] == t[newItemPosition]
                        }

                        override fun getOldListSize() = teachersList.size

                        override fun getNewListSize() = t.size
                    })
                    diffUtil.dispatchUpdatesTo(teachersAdapter)
                    teachersList.clear()
                    teachersList.addAll(it)
                }
                /** DiffUtil je velice užitečné využívat, jelikož může velice zrychlit proces při načítání změny RV.*/
            }
        })
        if (SharedPreferencesManager.isRunForFirstTime(appContext)) {
            SharedPreferencesManager.saveFirstRun(appContext)
        }
    }


    /** Metoda onOptionSelected slouží při kliknutí na položku v horní liště (filtrování a hledání) */

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_find_teachers, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_teacher -> {
                onActionAddTeacher()
                return true
            }
            R.id.action_filter -> {
                onActionFilter()
                return true
            }
            R.id.action_find -> {
                onActionFind()
                return true
            }
            /*R.id.action_cancel_filter -> {
              onActionCancelFilter()
              return true
            }*/
            else -> super.onOptionsItemSelected(item)
        }
    }
/*
  private fun onActionCancelFilter() {
    tutorList = saveTutorList
    selectTutorRecyclerView.adapter = tutorAdapter
    filtered = false
    invalidateOptionsMenu()
  }
 */

    private fun onActionAddTeacher() {
        findNavController().navigate(R.id.action_add_edit_teacher)
    }

    private fun onActionFilter() {
        findNavController().navigate(R.id.action_filter_teacher)
    }

    private fun onActionFind() {
        // TODO: Přidat SearchView
    }
/*
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
  */

    inner class TeachersAdapter : RecyclerView.Adapter<TeachersAdapter.TeachersViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeachersViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_select_teacher, parent, false)
            return TeachersViewHolder(view)
        }

        override fun getItemCount() = teachersList.size

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: TeachersViewHolder, position: Int) {
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
                    viewModel.delete(teachersList[holder.adapterPosition])
                }
            }

            /*holder.buttonProfile.setOnClickListener {
              startActivity(
                TutorProfileActivity.createIntent(
                  this@SelectTeacherFragment,
                  teachersList[holder.adapterPosition].tutorId
                )
              )
            }*/

            val tutor = teachersList[position]
            launch(Dispatchers.Main) {
                tutor.profilePicture = viewModel.findProfilePicture(tutor.userId)
                Picasso.get()
                    .load(File(context?.filesDir, tutor.profilePicture!!.name))
                    .placeholder(R.drawable.ic_custom_profile_secondary_dark_24)
                    .error(R.drawable.ic_custom_profile_secondary_dark_24)
                    .centerCrop()
                    .fit()
                    .into(holder.tutorProfilePicture)
            }.invokeOnCompletion {
                holder.tutorName.text = "${tutor.firstName} ${tutor.lastName}"
                holder.tutorCity.text = tutor.city
                holder.tutorPrice.text = String.format(
                    "%.0f Kč/h",
                    tutor.pricePerHour
                ) //TODO: Kč/h změnit na tutor.mena -- v BUDOUCNU.
                holder.tutorRating.text = String.format(Locale.US, "★ %.1f", tutor.rating)
            }
        }


        /** ViewHolder slouží pro organizaconizaci požadavků na VIEW od jednotlivých elementů.*/
        // TODO: Přepsat na Teacher VŠECHNO
        inner class TeachersViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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
