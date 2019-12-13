package com.mamak.geobaza.ui.viewmodel

import com.mamak.geobaza.ui.base.BaseViewModel
import javax.inject.Inject

class StatisticsViewModel @Inject constructor() : BaseViewModel() {
    override fun onCleared() {
        onStop()
    }
}