package com.example.edventure.activities.ui.find

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.arch.fragments.BaseMVVMFragment
import com.example.edventure.R
import com.example.edventure.EdventureApplication.Companion.appContext
import androidx.fragment.app.setFragmentResultListener
import com.example.edventure.model.User
import com.example.edventure.sharedpreferences.SharedPreferencesManager
import com.example.edventure.viewmodels.FindTeacherVM
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import com.example.edventure.activities.TeachersFilter
import kotlinx.android.synthetic.main.fragment_find_teacher.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

class FindTeacherFragment : BaseMVVMFragment<FindTeacherVM>(FindTeacherVM::class.java) {

    override val layout: Int = R.layout.fragment_find_teacher
    private var teachersList: MutableList<User> = mutableListOf()
    private var savedTeachersList: MutableList<User> = mutableListOf()


    var filterRating: Double? = -1.0
    var filterPriceMin: Double? = -1.0
    var filterPriceMax: Double? = -1.0
    var filterPlace: String? = ""

    private var filtered = false

    private val emptyDoubleValue = -1.0
    private val emptyStringValue = ""

    private val FILTER_TEACHERS_REQUEST = 100
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
        setFragmentResultListener("FILTER_TEACHER") { _, bundle ->
            val result = bundle.getParcelable<TeachersFilter>("bundleKey")
            savedTeachersList = teachersList.toMutableList()
            if (result!!.rating != emptyDoubleValue) {
                teachersList.removeAll { it.rating!! < result.rating }
            }
            if (result.city != emptyStringValue) {
                teachersList.removeAll { it.city != result.city}
            }
            if (result.maxPrice != emptyDoubleValue) {
                teachersList.removeAll { it.pricePerHour!! > result.maxPrice}
            }
            if (result.minPrice != emptyDoubleValue) {
                teachersList.removeAll { it.pricePerHour!! < result.minPrice }
            }
            filtered = true
            requireActivity().invalidateOptionsMenu()
        }
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
        if (!filtered) {
            inflater.inflate(R.menu.menu_find_teachers, menu)
        } else {
            inflater.inflate(R.menu.menu_cancel_filter, menu)
        }
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
            R.id.action_cancel_filter -> {
              onActionCancelFilter()
              return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

  private fun onActionCancelFilter() {
      filtered = false
      teachersList = savedTeachersList
      findTeacherRecyclerView.adapter = teachersAdapter
      requireActivity().invalidateOptionsMenu()
  }

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
      menuInflater.inflate(R.menu.menu_select_teacher, menu)
    } else {
      menuInflater.inflate(R.menu.menu_filter_teacher, menu)
    }
    return true
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == FILTER_TUTOR_REQUEST && resultCode == Activity.RESULT_OK) {
      saveTutorList = teacherList.toMutableList()
      when (data?.getStringExtra("filter_type")) {
        "filter_rating" -> {
          filterRating = data.getDoubleExtra("filter_rating", -1.0)
          teacherList.removeAll { it.rating < filterRating!! }
        }
        "filter_place" -> {
          filterPlace = data.getStringExtra("filter_place")
          teacherList.removeAll { it.city != filterPlace }
        }
        "filter_price_min" -> {
          filterPriceMin = data.getDoubleExtra("filter_price_min", -1.0)
          teacherList.removeAll { it.pricePerHour!! <= filterPriceMin!! }
        }
        "filter_price_max" -> {
          filterPriceMax = data.getDoubleExtra("filter_price_max", -1.0)
          teacherList.removeAll { it.pricePerHour!! >= filterPriceMax!! }
        }
      }
      filtered = true
      selectTutorRecyclerView.adapter = teacherAdapter
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


        override fun onBindViewHolder(holder: TeachersViewHolder, position: Int) {


            val teacher = teachersList[position]
            launch(Dispatchers.Main) {
                teacher.profilePicture = viewModel.findProfilePicture(teacher.userId)
                Picasso.get()
                    .load(File(context?.filesDir, teacher.profilePicture!!.name))
                    .placeholder(R.drawable.ic_custom_profile_secondary_dark_24)
                    .error(R.drawable.ic_custom_profile_secondary_dark_24)
                    .centerCrop()
                    .fit()
                    .into(holder.teacherProfilePicture)
            }.invokeOnCompletion {
                holder.teacherName.text = "${teacher.firstName} ${teacher.lastName}"
                holder.teacherCity.text = teacher.city
                holder.teacherPrice.text = String.format(
                    "%.0f Kč/h",
                    teacher.pricePerHour
                ) //TODO: Kč/h změnit na teacher.mena -- v BUDOUCNU.
                holder.teacherRating.text = String.format(Locale.US, "★ %.1f", teacher.rating)
            }
        }


        /** ViewHolder slouží pro organizaconizaci požadavků na VIEW od jednotlivých elementů.*/
        inner class TeachersViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val teacherProfilePicture: CircleImageView =
                view.findViewById(R.id.imageview_cardview)
            val teacherName: TextView = view.findViewById(R.id.textview_teacher_name)
            val teacherCity: TextView = view.findViewById(R.id.textview_teacher_city)
            val teacherPrice: TextView = view.findViewById(R.id.textview_teacher_price)
            val teacherRating: TextView = view.findViewById(R.id.textview_teacher_rating)
            val teacherCardView: CardView = view.findViewById(R.id.cardview_find_teacher)
        }
    }
}
