package com.example.tutorme.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tutorme.R
import com.example.tutorme.model.Tutor
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main.*

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
    /**  layoutManager se stará o pozicování - Existuje možnost i GridManageru, atd. */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)
        setSupportActionBar(toolbar)

    }

    /** Metoda onOptionSelected slouží při kliknutí na položku v horní liště (filtrování a hledání) */
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
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


    /** ViewHolder slouží pro organizaconizaci požadavků na VIEW od jednotlivých elementů.*/
    inner class TutorViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tutorPicture: CircleImageView = view.findViewById(R.id.tutorPicture)
        val tutorName: TextView = view.findViewById(R.id.tutorName)
        val tutorCity: TextView = view.findViewById(R.id.tutorCity)
        val tutorPrice: TextView = view.findViewById(R.id.tutorPrice)
        val tutorRating: TextView = view.findViewById(R.id.tutorRating)
    }


}