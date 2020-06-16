package com.mamak.geobaza.ui.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.mamak.geobaza.R
import com.mamak.geobaza.factory.ViewModelFactory
import com.mamak.geobaza.base.BaseFragment
import com.mamak.geobaza.viewmodel.SettingsViewModel
import com.mamak.geobaza.utils.constans.AppConstans.THEME_ALADDIN
import com.mamak.geobaza.utils.constans.AppConstans.THEME_ALICE_IN_WONDERLAND
import com.mamak.geobaza.utils.constans.AppConstans.THEME_BLACK_AND_YELLOW
import com.mamak.geobaza.utils.constans.AppConstans.THEME_DEEP_PURPLE
import com.mamak.geobaza.utils.constans.AppConstans.THEME_MARSHMALLOW
import com.mamak.geobaza.utils.constans.AppConstans.THEME_MERRY_CHRISTMAS
import com.mamak.geobaza.utils.constans.AppConstans.THEME_ORANGE_IS_THE_NEW_BLACK
import com.mamak.geobaza.utils.constans.AppConstans.THEME_TINKY_PINKY
import com.mamak.geobaza.utils.constans.AppConstans.UNIT_KM
import com.mamak.geobaza.utils.constans.AppConstans.UNIT_MI
import com.mamak.geobaza.utils.manager.ThemeManager
import com.mamak.geobaza.utils.view.ExtendedSpinner
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

class SettingsFragment : BaseFragment() {
    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
    @Inject
    internal lateinit var settingsViewModel: SettingsViewModel

    private lateinit var sharedPref: SharedPreferences
    private var originalUnit: String? = null
    private var originalZone: String? = null
    private var originalTheme: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        initViewModel()
        sharedPref = PreferenceManager.getDefaultSharedPreferences(activity)
        saveSharedPref()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSpinners()
        setOnClick()
        activity.supportActionBar?.hide()
    }

    private fun initViewModel() {
        settingsViewModel = viewModelFactory.create(SettingsViewModel::class.java)
    }

    private fun saveSharedPref() {
        sharedPref.apply {
            originalUnit = getString("current_unit", UNIT_KM)
            originalZone = getInt("current_zone", 6).toString()
            originalTheme = getString("current_theme", THEME_MARSHMALLOW)
        }
    }

    private fun setOnClick() {
        b_apply.setOnClickListener {
            setCustomTheme()
            setUnit()
            setZone()
            saveSharedPref()
            setApplyButton()
        }
    }

    private fun setListeners() {
        listOf<ExtendedSpinner>(
            spinner_units,
            spinner_zones,
            spinner_themes
        ).forEach {
            it.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    setApplyButton()
                }
            }
        }
    }

    private fun hasSharedPrefChanged(): Boolean {
        val currentUnit = spinner_units.selectedItem
        val currentZone = spinner_zones.selectedItem
        val currentTheme = spinner_themes.selectedItem

        return (currentUnit != originalUnit ||
                currentZone != originalZone ||
                currentTheme != originalTheme)
    }

    private fun setApplyButton() {
        val color: Int
        val drawable: Int
        val enable = hasSharedPrefChanged()

        if (enable) {
            color = ThemeManager.getColorResByAttr(activity, R.attr.colorTextOnSecondary)
            drawable = R.drawable.item_circle_full
        } else {
            color = ThemeManager.getColorResByAttr(activity, R.attr.colorSecondary)
            drawable = R.drawable.item_circle
        }

        b_apply.apply {
            context?.let {
                background = it.getDrawable(drawable)
                setTextColor(it.getColor(color))
                isEnabled = enable
            }
        }
    }

    private fun setUnit() {
        val newUnit = when (spinner_units.selectedItem) {
            getString(R.string.unit_km) -> UNIT_KM
            getString(R.string.unit_mi) -> UNIT_MI
            else -> UNIT_KM
        }
        sharedPref.edit().putString("current_unit", newUnit).apply()
    }

    private fun setZone() {
        val newZone = when (spinner_zones.selectedItem) {
            "5" -> 5
            "6" -> 6
            "7" -> 7
            "8" -> 8
            else -> 6
        }
        sharedPref.edit().putInt("current_zone", newZone).apply()
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

    private fun setSpinners() {
        setListeners()
        setUnitsSpinner()
        setZoneSpinner()
        setThemeSpinner()
    }

    private fun setUnitsSpinner() {
        val arrayAdapter = ArrayAdapter.createFromResource(activity, R.array.array_units, R.layout.item_spinner)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_units.adapter = arrayAdapter
        val unit = when (sharedPref.getString("current_unit", UNIT_KM)) {
            UNIT_KM -> getString(R.string.unit_km)
            UNIT_MI -> getString(R.string.unit_mi)
            else -> getString(R.string.unit_km)
        }
        val currentIndex = spinner_units.getIndex(unit)
        spinner_units.setSelection(currentIndex)
    }

    private fun setZoneSpinner() {
        val arrayAdapter = ArrayAdapter.createFromResource(activity, R.array.array_zones, R.layout.item_spinner)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_zones.adapter = arrayAdapter
        val zone = when (sharedPref.getInt("current_zone", 6)) {
            5 -> "5"
            6 -> "6"
            7 -> "7"
            8 -> "8"
            else -> "6"
        }
        val currentIndex = spinner_zones.getIndex(zone)
        spinner_zones.setSelection(currentIndex)
    }

    private fun setThemeSpinner() {
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