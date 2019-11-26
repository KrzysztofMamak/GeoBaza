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
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject


class LoginFragment : BaseFragment() {
    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
    @Inject
    internal lateinit var  registrationLoginSharedViewModel: RegistrationLoginSharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClicks()
    }

    private fun setOnClicks() {
        b_login.setOnClickListener {
            login()
        }

        tv_register.setOnClickListener {
            launchRegistrationFragment()
        }
    }

    private fun login() {
        val email = et_email.text.toString()
        val password = et_password.text.toString()
        registrationLoginSharedViewModel.login(email, password)

        registrationLoginSharedViewModel.getLoginLiveData().observe(this, Observer { resource ->
            if (resource.isLoading) {
                showProgressBar()
            } else if (resource.data != null) {
                if (resource.data.isSuccessful) {
                    val intent = Intent(activity, ProjectListActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    activity.finish()
                } else {
                    handleErrorResponse()
                }
            } else {
                handleErrorResponse()
            }
        })
    }

    private fun handleSuccessResponse() {

    }

    private fun handleErrorResponse() {

    }

    private fun showProgressBar() {

    }

    private fun launchRegistrationFragment() {
        val registrationFragment = RegistrationFragment()
        val fragmentTransaction = getActivity()?.supportFragmentManager?.beginTransaction()

        fragmentTransaction?.replace(R.id.container_fragment, registrationFragment)
        fragmentTransaction?.addToBackStack(null)
        fragmentTransaction?.commit()
    }

    private fun initViewModel() {
        registrationLoginSharedViewModel = viewModelFactory.create(RegistrationLoginSharedViewModel::class.java)
    }
}