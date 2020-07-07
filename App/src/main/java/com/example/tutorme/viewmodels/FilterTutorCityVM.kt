package com.example.tutorme.viewmodels

import android.app.Application
import com.example.tutorme.viewmodels.base.BaseTutorVM

class FilterTutorCityVM(app: Application): BaseTutorVM(app) {

    fun findCities(): MutableList<String>{
        return tutorRepository.findCities()
    }

    //TODO: Bude to určitě tady? ... NEBUDE TO až ve FilterActivity? .. A zde bude jenom findByName?
}