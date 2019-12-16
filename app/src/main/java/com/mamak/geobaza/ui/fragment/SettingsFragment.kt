package com.mamak.geobaza.ui.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.mamak.geobaza.R
import com.mamak.geobaza.factory.ViewModelFactory
import com.mamak.geobaza.ui.base.BaseFragment
import com.mamak.geobaza.ui.viewmodel.SettingsViewModel
import com.mamak.geobaza.utils.constans.AppConstans.THEME_ALADDIN
import com.mamak.geobaza.utils.constans.AppConstans.THEME_ALICE_IN_WONDERLAND
import com.mamak.geobaza.utils.constans.AppConstans.THEME_BLACK_AND_YELLOW
import com.mamak.geobaza.utils.constans.AppConstans.THEME_DEEP_PURPLE
import com.mamak.geobaza.utils.constans.AppConstans.THEME_MARSHMALLOW
import com.mamak.geobaza.utils.constans.AppConstans.THEME_MERRY_CHRISTMAS
import com.mamak.geobaza.utils.constans.AppConstans.THEME_ORANGE_IS_THE_NEW_BLACK
import com.mamak.geobaza.utils.constans.AppConstans.THEME_TINKY_PINKY
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

class SettingsFragment : BaseFragment() {
    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
    @Inject
    internal lateinit var settingsViewModel: SettingsViewModel

    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        initViewModel()
        sharedPref = PreferenceManager.getDefaultSharedPreferences(activity)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSpinner()
        setOnClick()
    }

    private fun initViewModel() {
        settingsViewModel = viewModelFactory.create(SettingsViewModel::class.java)
    }

    private fun setOnClick() {
        b_apply_theme.setOnClickListener {
            setCustomTheme()
        }
    }

    private fun setCustomTheme() {
        val newTheme = when (spinner_themes.selectedItem) {
            getString(R.string.marshmallow) -> THEME_MARSHMALLOW
            getString(R.string.aladdin) -> THEME_ALADDIN
            getString(R.string.deep_purple) -> THEME_DEEP_PURPLE
            getString(R.string.tinky_pinky) -> THEME_TINKY_PINKY
            getString(R.string.orange_is_the_new_black) -> THEME_ORANGE_IS_THE_NEW_BLACK
            getString(R.string.alice_in_wonderland) -> THEME_ALICE_IN_WONDERLAND
            getString(R.string.black_and_yellow) -> THEME_BLACK_AND_YELLOW
            getString(R.string.merry_christmas) -> THEME_MERRY_CHRISTMAS
            else -> THEME_MARSHMALLOW
        }
        sharedPref.edit().putString("current_theme", newTheme).apply()
        activity.recreate()
    }

    private fun setSpinner() {
        val arrayAdapter = ArrayAdapter.createFromResource(activity, R.array.theme_names, R.layout.item_spinner)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_themes.adapter = arrayAdapter
        val themeName = when (sharedPref.getString("current_theme", THEME_MARSHMALLOW)) {
            THEME_MARSHMALLOW -> getString(R.string.marshmallow)
            THEME_ALADDIN -> getString(R.string.aladdin)
            THEME_DEEP_PURPLE -> getString(R.string.deep_purple)
            THEME_TINKY_PINKY -> getString(R.string.tinky_pinky)
            THEME_ORANGE_IS_THE_NEW_BLACK -> getString(R.string.orange_is_the_new_black)
            THEME_ALICE_IN_WONDERLAND -> getString(R.string.alice_in_wonderland)
            THEME_BLACK_AND_YELLOW -> getString(R.string.black_and_yellow)
            THEME_MERRY_CHRISTMAS -> getString(R.string.merry_christmas)
            else -> getString(R.string.marshmallow)
        }
        val currentIndex = spinner_themes.getIndex(themeName)
        spinner_themes.setSelection(currentIndex)
    }
}