package com.example.tutorme.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tutorme.R
import com.example.tutorme.model.Tutor
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_select_tutor.*

private val FILTER_TUTOR_REQUEST_CODE: Int = 100

class  SelectTutorActivity: BaseActivity() {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, SelectTutorActivity::class.java)
        }
    }

    private val layout: Int = R.layout.activity_select_tutor
    private val tutorList: MutableList<Tutor> = mutableListOf()

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var tutorAdapter: TutorAdapter
    /**  layoutManager se stará o pozicování - Existuje možnost i GridManageru, atd. */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)
        setSupportActionBar(toolbar)

        tutorAdapter = TutorAdapter()
        layoutManager = LinearLayoutManager(this)
        selectTutorRecyclerView.layoutManager = layoutManager
        selectTutorRecyclerView.adapter = tutorAdapter

        /** Vložení statických hodnot*/
        tutorList.add(Tutor(1,"Miloš", "Nový", "Karviná", "412512861","tom@gmail.com", 200.0, 3.5 ))
        tutorList.add(Tutor(1,"Miloš", "Nový", "Karviná", "412512861","tom@gmail.com", 200.0, 3.5 ))
        tutorList.add(Tutor(1,"Miloš", "Nový", "Karviná", "412512861","tom@gmail.com", 200.0, 3.5 ))
        tutorList.add(Tutor(1,"Miloš", "Nový", "Karviná", "412512861","tom@gmail.com", 200.0, 3.5 ))
        tutorList.add(Tutor(1,"Miloš", "Nový", "Karviná", "412512861","tom@gmail.com", 200.0, 3.5 ))
        tutorList.add(Tutor(1,"Miloš", "Nový", "Karviná", "412512861","tom@gmail.com", 200.0, 3.5 ))
        tutorList.add(Tutor(1,"Miloš", "Nový", "Karviná", "412512861","tom@gmail.com", 200.0, 3.5 ))
        /** Vložení statických hodnot*/
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

    private fun onActionAddTutor(){
        startActivityForResult(FilterTutorActivity.createIntent(this ), FILTER_TUTOR_REQUEST_CODE)
    }

    private fun onActionFilter(){
        startActivityForResult(FilterTutorActivity.createIntent(this ), FILTER_TUTOR_REQUEST_CODE)
    }

    private fun onActionSearch(){
        // TODO: Vytvořit SearchTutorActivity
        startActivityForResult(FilterTutorActivity.createIntent(this ), FILTER_TUTOR_REQUEST_CODE)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_select_tutor, menu)
        return true
    }

    inner class TutorAdapter: RecyclerView.Adapter<TutorAdapter.TutorViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_select_tutor, parent,false)
            return TutorViewHolder(view)
        }

        override fun getItemCount() = tutorList.size

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: TutorViewHolder, position: Int) {
            val tutor = tutorList[position]
            holder.tutorName.text = "${tutor.firstName} ${tutor.lastName}"
            holder.tutorCity.text = tutor.city
            holder.tutorPrice.text = String.format("%.0f Kč/h", tutor.pricePerHour) //TODO: Kč/h změnit na tutor.mena -- v BUDOUCNU.
            holder.tutorRating.text = String.format("★ %.1f", tutor.rating)
        }

        /** ViewHolder slouží pro organizaconizaci požadavků na VIEW od jednotlivých elementů.*/
        inner class TutorViewHolder(view: View): RecyclerView.ViewHolder(view){
            // TODO: Vyměnit Dr. House
            //val tutorPicture: CircleImageView = view.findViewById(R.id.tutorPicture)
            val tutorName: TextView = view.findViewById(R.id.tutorName)
            val tutorCity: TextView = view.findViewById(R.id.tutorCity)
            val tutorPrice: TextView = view.findViewById(R.id.tutorPrice)
            val tutorRating: TextView = view.findViewById(R.id.tutorRating)
        }
    }





}