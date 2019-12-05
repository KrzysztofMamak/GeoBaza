package com.mamak.geobaza.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
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

    override fun onStart() {
        super.onStart()
        checkUserSession()
    }

    private fun initViewModel() {
        registrationLoginSharedViewModel = viewModelFactory.create(RegistrationLoginSharedViewModel::class.java)
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.apply {
            replace(R.id.container_fragment, fragment)
            addToBackStack(null)
            commit()
        }
    }

    private fun launchProjectListActivity() {
        val intent = Intent(this, ProjectListActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun checkUserSession() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            launchProjectListActivity()
        }
    }

    override fun setStatusBar() {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = ContextCompat.getColor(this@RegistrationLoginActivity, R.color.colorPrimaryDark)
        }
    }
}