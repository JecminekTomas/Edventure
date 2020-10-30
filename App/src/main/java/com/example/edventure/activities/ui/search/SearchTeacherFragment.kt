package com.example.edventure.activities.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.arch.activities.BaseMVVMActivity
import com.example.arch.fragments.BaseMVVMFragment
import com.example.edventure.R
import com.example.edventure.activities.AddEditTutorActivity
import com.example.edventure.activities.FilterTutorActivity
import com.example.edventure.activities.ui.profile.ProfileFragment
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

class SearchTeacherFragment : BaseMVVMFragment<SelectTutorVM>(SelectTutorVM::class.java) {

  override val layout: Int = R.layout.activity_select_tutor

  private var teachersList: MutableList<Tutor> = mutableListOf()
  private var savedTeachersList: MutableList<Tutor> = mutableListOf()
  private var mExpandedPosition = -1
  private var previousExpandedPosition = -1
  private var filtered: Boolean = false

  var filterRating: Double? = -1.0
  var filterPriceMin: Double? = -1.0
  var filterPriceMax: Double? = -1.0
  var filterPlace: String? = ""

  private val FILTER_TEACHERS_REQUEST = 100

  private lateinit var layoutManager: LinearLayoutManager
  private lateinit var teachersAdapter: TeachersAdapter

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    teachersAdapter = TeachersAdapter()
    layoutManager = LinearLayoutManager(this.context)
    selectTutorRecyclerView.layoutManager = layoutManager
    selectTutorRecyclerView.adapter = teachersAdapter

    viewModel.getAll().observe(viewLifecycleOwner, object : Observer<MutableList<Tutor>> {
      override fun onChanged(t: MutableList<Tutor>?) {
        t?.let {
          val diffUtil = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(
              oldItemPosition: Int,
              newItemPosition: Int
            ): Boolean {
              return teachersList[oldItemPosition].tutorId == t[newItemPosition].tutorId
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
    if (SharedPreferencesManager.isRunForFirstTime(this.context!!)) {
      SharedPreferencesManager.saveFirstRun(this.context!!)
    }

    //setHasOptionsMenu(true)
    return inflater.inflate(layout, container, false)
  }
/*
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
    teachersList = savedTeachersList
    selectTutorRecyclerView.adapter = teachersAdapter
    filtered = false
    invalidateOptionsMenu()
  }

  private fun onActionAddTutor() {
    //startActivity(AddEditTutorActivity.createIntent(this, null))
  }

  private fun onActionFilter() {
    //startActivityForResult(FilterTutorActivity.createIntent(this), FILTER_TEACHERS_REQUEST)
  }

  private fun onActionSearch() {
    // TODO: Vytvořit SearchTutorActivity
  }

  override fun onPrepareOptionsMenu(menu: Menu) {
    super.onPrepareOptionsMenu(menu)
    menu.clear()
    if (!filtered) {
      menuInflater.inflate(R.menu.menu_select_tutor, menu)
    } else {
      menuInflater.inflate(R.menu.menu_filter_tutor, menu)
    }
    return true
  }
*/

  inner class TeachersAdapter : RecyclerView.Adapter<TeachersAdapter.TeachersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeachersViewHolder {
      val view: View = LayoutInflater.from(parent.context)
        .inflate(R.layout.row_select_tutor, parent, false)
      return TeachersViewHolder(view)
    }


    override fun getItemCount() = teachersList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TeachersViewHolder, position: Int) {
      val isExpanded = position == mExpandedPosition
      holder.buttons.visibility = if (isExpanded) View.VISIBLE else View.GONE
      holder.teacherCardView.isActivated = isExpanded

      if (isExpanded)
        previousExpandedPosition = position

      holder.teacherCardView.setOnClickListener {
        mExpandedPosition = if (isExpanded) -1 else position
        notifyItemChanged(previousExpandedPosition)
        notifyItemChanged(position)
      }

      holder.buttonDelete.setOnClickListener {
        launch {
          viewModel.delete(teachersList[holder.adapterPosition])
        }
      }

      holder.buttonProfile.setOnClickListener {
        ProfileFragment.newInstance()
      }

      val teacher = teachersList[position]
      launch(Dispatchers.Main) {
        teacher.profilePicture = viewModel.findProfilePicture(teacher.tutorId)
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
          ) //TODO: Kč/h změnit na tutor.mena -- v BUDOUCNU.
          holder.teacherRating.text = String.format(Locale.US, "★ %.1f", teacher.rating)
      }
    }


    /** ViewHolder slouží pro organizaconizaci požadavků na VIEW od jednotlivých elementů.*/
    inner class TeachersViewHolder(view: View) : RecyclerView.ViewHolder(view) {
      val teacherProfilePicture: CircleImageView =
        view.findViewById(R.id.profilePictureIconSelect)
      val teacherName: TextView = view.findViewById(R.id.tutorName)
      val teacherCity: TextView = view.findViewById(R.id.tutorCity)
      val teacherPrice: TextView = view.findViewById(R.id.tutorPrice)
      val teacherRating: TextView = view.findViewById(R.id.tutorRating)
      val buttonProfile: Button = view.findViewById(R.id.buttonProfile)
      val buttonDelete: Button = view.findViewById(R.id.buttonDelete)
      val buttons: LinearLayout = view.findViewById(R.id.card_view_buttons)
      val teacherCardView: CardView = view.findViewById(R.id.tutor_card_view)
    }
  }

}