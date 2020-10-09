package com.example.edventure.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.example.edventure.R

class ClassInfoView : FrameLayout {

    private lateinit var subject: TextView
    private lateinit var place: TextView
    private lateinit var date: TextView
    private lateinit var time: TextView
    private lateinit var capacity: TextView
    private lateinit var online: TextView
    private lateinit var price: TextView


    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) :
            super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {

        val attributes = context.obtainStyledAttributes(
            attrs,
            R.styleable.ClassInfoView,
            0,
            0
        )

        val subjectAttr = attributes.getString(R.styleable.ClassInfoView_subject)
        val placeAttr = attributes.getString(R.styleable.ClassInfoView_place)
        val dateAttr = attributes.getString(R.styleable.ClassInfoView_date)
        val timeAttr = attributes.getString(R.styleable.ClassInfoView_time)
        val capacityAttr = attributes.getString(R.styleable.ClassInfoView_capacity) // TODO: Změnit na integer.
        val onlineAttr = attributes.getBoolean(R.styleable.ClassInfoView_online, false)
        val priceAttr = attributes.getInteger(R.styleable.ClassInfoView_price, 0)
        attributes.recycle()

        View.inflate(context, R.layout.class_info, this)
        subject = findViewById(R.id.subject)
        place = findViewById(R.id.place)
        date = findViewById(R.id.date)
        time = findViewById(R.id.time)
        capacity = findViewById(R.id.capacity)
        online = findViewById(R.id.online)
        price = findViewById(R.id.price)

        subject.text = subjectAttr
        place.text = placeAttr
        date.text = dateAttr
        time.text = timeAttr
        capacity.text = capacityAttr
        price.text = priceAttr.toString()

        if (onlineAttr) {
            online.visibility = View.VISIBLE
        }

        showInformation()
    }

    private fun showInformation() {
        if (subject.text.isNotEmpty()) {
            showSubject()
        }
        if (place.text.isNotEmpty()) {
            showPlace()
        }
        if (date.text.isNotEmpty()) {
            showDate()
        }
        if (time.text.isNotEmpty()) {
            showTime()
        }
        if (capacity.text.isNotEmpty()) {
            showCapacity()
        }
    }

    private fun showSubject() {
        subject.visibility = View.VISIBLE
    }

    private fun showPlace() {
        place.visibility = View.VISIBLE
    }

    private fun showDate() {
        date.visibility = View.VISIBLE
    }

    private fun showTime() {
        time.visibility = View.VISIBLE
    }

    private fun showCapacity() {
        this.capacity.visibility = View.VISIBLE
    }


}