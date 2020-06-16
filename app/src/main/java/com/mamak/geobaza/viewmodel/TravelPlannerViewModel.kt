package com.mamak.geobaza.viewmodel

import com.mamak.geobaza.base.BaseViewModel
import javax.inject.Inject

class TravelPlannerViewModel @Inject constructor() : BaseViewModel() {
    override fun onCleared() {
        onStop()
    }
}