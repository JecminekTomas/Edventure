package com.example.edventure.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.transition.TransitionManager
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.arch.BaseMVVMActivity
import com.example.edventure.R
import com.example.edventure.model.Tutor
import com.example.edventure.viewmodels.SelectTutorVM
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_select_tutor.*
import kotlinx.android.synthetic.main.content_select_tutor.*
import kotlinx.coroutines.launch


class SelectTutorActivity : BaseMVVMActivity<SelectTutorVM>(SelectTutorVM::class.java){

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, SelectTutorActivity::class.java)
        }
    }

    override val layout: Int = R.layout.activity_select_tutor
    private val tutorList: MutableList<Tutor> = mutableListOf()
    private var mExpandedPosition = -1
    private var previousExpandedPosition = -1

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
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onActionAddTutor() {
        startActivity(AddEditTutorActivity.createIntent(this, null))
    }

    private fun onActionFilter() {
        startActivity(FilterTutorActivity.createIntent(this))
    }

    private fun onActionSearch() {
        // TODO: Vytvořit SearchTutorActivity
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_select_tutor, menu)
        return true
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
            holder.informations.isActivated = isExpanded

            if (isExpanded)
                previousExpandedPosition = position

            holder.informations.setOnClickListener {
                mExpandedPosition = if (isExpanded) -1 else position
                notifyItemChanged(previousExpandedPosition)
                notifyItemChanged(position)
            }

            holder.buttonDelete.setOnClickListener{
                launch {
                    viewModel.delete(tutorList[holder.adapterPosition])
                }
            }

            holder.buttonProfile.setOnClickListener{
                startActivity(TutorProfileActivity.createIntent(applicationContext))
            }

            val tutor = tutorList[position]
           // Picasso.get().load(File(tutor.profilePicture!!.name)).into(holder.tutorProfilePicture)
            holder.tutorName.text = "${tutor.firstName} ${tutor.lastName}"
            holder.tutorCity.text = tutor.city
            holder.tutorPrice.text = String.format(
                "%.0f Kč/h",
                tutor.pricePerHour
            ) //TODO: Kč/h změnit na tutor.mena -- v BUDOUCNU.
            holder.tutorRating.text = String.format("★ %.1f", tutor.rating)
        }

        /** ViewHolder slouží pro organizaconizaci požadavků na VIEW od jednotlivých elementů.*/
        inner class TutorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tutorProfilePicture: CircleImageView = view.findViewById(R.id.profilePictureIconSelect)
            val tutorName: TextView = view.findViewById(R.id.tutorName)
            val tutorCity: TextView = view.findViewById(R.id.tutorCity)
            val tutorPrice: TextView = view.findViewById(R.id.tutorPrice)
            val tutorRating: TextView = view.findViewById(R.id.tutorRating)
            val buttonProfile: Button = view.findViewById(R.id.buttonProfile)
            val buttonDelete: Button = view.findViewById(R.id.buttonDelete)
            val buttons: LinearLayout = view.findViewById(R.id.card_view_buttons)
            val informations: ConstraintLayout = view.findViewById(R.id.card_view_information)
        }
    }
}

