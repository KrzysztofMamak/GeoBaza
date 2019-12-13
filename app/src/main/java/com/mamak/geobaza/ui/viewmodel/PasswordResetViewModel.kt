package com.mamak.geobaza.ui.viewmodel

import com.mamak.geobaza.ui.base.BaseViewModel

class PasswordResetViewModel : BaseViewModel() {
    override fun onCleared() {
        onStop()
    }
}