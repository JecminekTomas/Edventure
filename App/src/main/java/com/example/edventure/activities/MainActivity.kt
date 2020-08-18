package com.example.edventure.activities

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.edventure.R
import com.example.edventure.sharedpreferences.SharedPreferencesManager
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroFragment
import com.github.appintro.model.SliderPage

class MainActivity : AppIntro() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (SharedPreferencesManager.isRunForFirstTime(this)) {
            addSlide(createFirstSlide())
            addSlide(createSecondSlide())
            addSlide(createThirdSlide())
            addSlide(createFourthSlide())
            setBarColor(ContextCompat.getColor(this, android.R.color.transparent))
            setSeparatorColor(ContextCompat.getColor(this, android.R.color.white))
            setDoneText(getString(R.string.done))
            setColorSkipButton(ContextCompat.getColor(this, R.color.colorPrimary))
            setColorDoneText(ContextCompat.getColor(this, R.color.colorPrimary))
            setNextArrowColor(ContextCompat.getColor(this, R.color.colorPrimary))
            setSkipText(getString(R.string.skip))
            setIndicatorColor(
                selectedIndicatorColor = ContextCompat.getColor(this, R.color.colorPrimary),
                unselectedIndicatorColor = ContextCompat.getColor(this, R.color.colorPrimary)
            )
        }
        else{
            continueToApp()
        }
    }

    private fun createFirstSlide(): AppIntroFragment {
        val sliderPage = SliderPage()
        sliderPage.title = getString(R.string.app_name)
        sliderPage.backgroundColor = Color.WHITE
        sliderPage.titleColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        sliderPage.descriptionColor = ContextCompat.getColor(this, R.color.colorSecondaryDark)
        return AppIntroFragment.newInstance(sliderPage)
    }

    private fun createSecondSlide(): AppIntroFragment {
        val sliderPage = SliderPage()
        sliderPage.title = getString(R.string.app_intro_title_1)
        sliderPage.description = getString(R.string.app_intro_text_1)
        sliderPage.imageDrawable = R.drawable.one_click
        sliderPage.backgroundColor = Color.WHITE
        sliderPage.titleColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        sliderPage.descriptionColor = ContextCompat.getColor(this, R.color.colorSecondaryDark)
        return AppIntroFragment.newInstance(sliderPage)
    }

    private fun createThirdSlide(): AppIntroFragment {
        val sliderPage = SliderPage()
        sliderPage.title = getString(R.string.app_intro_title_2)
        sliderPage.description = getString(R.string.app_intro_text_2)
        sliderPage.imageDrawable = R.drawable.calendar
        sliderPage.backgroundColor = Color.WHITE
        sliderPage.titleColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        sliderPage.descriptionColor = ContextCompat.getColor(this, R.color.colorSecondaryDark)
        return AppIntroFragment.newInstance(sliderPage)
    }

    private fun createFourthSlide(): AppIntroFragment {
        val sliderPage = SliderPage()
        sliderPage.title = getString(R.string.app_intro_title_3)
        sliderPage.description = getString(R.string.app_intro_text_3)
        sliderPage.imageDrawable = R.drawable.no_card
        sliderPage.backgroundColor = Color.WHITE
        sliderPage.titleColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        sliderPage.descriptionColor = ContextCompat.getColor(this, R.color.colorSecondaryDark)
        return AppIntroFragment.newInstance(sliderPage)
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        continueToApp()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        continueToApp()
    }

    fun continueToApp () {
        SharedPreferencesManager.saveFirstRun(this)
        startActivity(SelectTutorActivity.createIntent(this))
        finish()
    }
}

