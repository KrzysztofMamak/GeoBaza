package com.mamak.geobaza.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mamak.geobaza.R
import com.mamak.geobaza.ui.base.BaseFragment

class RegistrationFragment : BaseFragment() {
//    @Inject
//    internal lateinit var viewModelFactory: ViewModelFactory
//    @Inject
//    internal lateinit var  registrationLoginSharedViewModel: RegistrationLoginSharedViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

//    private fun initViewModel() {
//        registrationLoginSharedViewModel = viewModelFactory.create(RegistrationLoginSharedViewModel::class.java)
//    }
}