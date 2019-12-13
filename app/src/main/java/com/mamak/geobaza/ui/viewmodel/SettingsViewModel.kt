package com.mamak.geobaza.ui.viewmodel

import com.mamak.geobaza.ui.base.BaseViewModel
import javax.inject.Inject

class SettingsViewModel @Inject constructor() : BaseViewModel() {
    override fun onCleared() {
        onStop()
    }
}