package com.mamak.geobaza.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mamak.geobaza.R
import com.mamak.geobaza.factory.ViewModelFactory
import com.mamak.geobaza.ui.base.BaseActivity
import com.mamak.geobaza.ui.fragment.LoginFragment
import com.mamak.geobaza.ui.viewmodel.RegistrationLoginSharedViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class RegistrationLoginActivity : BaseActivity() {
    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
    @Inject
    internal lateinit var  registrationLoginSharedViewModel: RegistrationLoginSharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_login)
        AndroidInjection.inject(this)
        initViewModel()
        replaceFragment(LoginFragment())
    }

    private fun initViewModel() {
        registrationLoginSharedViewModel = viewModelFactory.create(RegistrationLoginSharedViewModel::class.java)
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container_fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}