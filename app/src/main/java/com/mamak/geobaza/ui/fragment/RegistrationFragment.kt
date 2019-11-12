package com.mamak.geobaza.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.mamak.geobaza.R
import com.mamak.geobaza.factory.ViewModelFactory
import com.mamak.geobaza.ui.activity.ProjectListActivity
import com.mamak.geobaza.ui.base.BaseFragment
import com.mamak.geobaza.ui.viewmodel.RegistrationLoginSharedViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_registration.*
import javax.inject.Inject

class RegistrationFragment : BaseFragment() {
    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
    @Inject
    internal lateinit var registrationLoginSharedViewModel: RegistrationLoginSharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClick()
    }

    private fun setOnClick() {
        b_register.setOnClickListener {
            register()
        }
    }

    private fun register() {
        val email = et_email.text.toString()
        val passwordFirst = et_password_first.text.toString()
        val passwordSecond = et_password_second.text.toString()
        if (passwordFirst == passwordSecond) {
            registrationLoginSharedViewModel.register(email, passwordFirst)

            registrationLoginSharedViewModel.getRegistrationLiveData().observe(this, Observer { resource ->
                if (resource.isLoading) {
                    showProgressBar()
                } else if (resource.data != null) {
                    if (resource.data.isSuccessful) {
                        handleSuccessResponse()
                    } else {
                        handleErrorResponse()
                    }
                } else {
                    handleErrorResponse()
                }
            })
        }
    }

    private fun handleSuccessResponse() {

    }

    private fun handleErrorResponse() {

    }

    private fun showProgressBar() {

    }

    private fun initViewModel() {
        registrationLoginSharedViewModel = viewModelFactory.create(RegistrationLoginSharedViewModel::class.java)
    }
}