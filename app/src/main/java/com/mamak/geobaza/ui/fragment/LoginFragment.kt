package com.mamak.geobaza.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.Observer
import com.mamak.geobaza.R
import com.mamak.geobaza.factory.ViewModelFactory
import com.mamak.geobaza.ui.activity.ProjectListActivity
import com.mamak.geobaza.ui.base.BaseFragment
import com.mamak.geobaza.ui.viewmodel.RegistrationLoginSharedViewModel
import com.mamak.geobaza.utils.manager.ValidationManager
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject


class LoginFragment : BaseFragment() {
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
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClicks()
        setListeners()
    }

    private fun setOnClicks() {
        b_login.setOnClickListener {
            login()
        }

        tv_register.setOnClickListener {
            launchRegistrationFragment()
        }
    }

    private fun setListeners() {
        listOf<EditText>(et_email, et_password).forEach {
            it.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    setLoginAvailability()
                }
            })
        }
    }

    private fun login() {
        val email = et_email.text.toString()
        val password = et_password.text.toString()
        registrationLoginSharedViewModel.loginViaEmailAndPassword(email, password)

        registrationLoginSharedViewModel.getLoginViaEmailLiveData().observe(
            this, Observer { resource ->
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

    private fun setLoginAvailability() {
        if (validateUser()) {
            allowLogin()
        } else {
            denyLogin()
        }
    }

    private fun validateUser(): Boolean {
        val email = et_email.text.toString()
        val password = et_password.text.toString()
        if (ValidationManager.validateEmail(email) && password.isNotEmpty()) {
            return true
        }
        return false
    }

    private fun allowLogin() {
        b_login.apply {
            context?.let {
                background = it.getDrawable(R.drawable.item_circle_full)
                setTextColor(it.getColor(R.color.colorTextOnSecondary))
                isEnabled = true
            }
        }
    }

    private fun denyLogin() {
        b_login.apply {
            context?.let {
                background = it.getDrawable(R.drawable.item_circle)
                setTextColor(it.getColor(R.color.colorSecondaryLight))
                isEnabled = false
            }
        }
    }

    private fun handleSuccessResponse() {
//        TODO handleSuccessResponse()
    }

    private fun handleErrorResponse() {
//        TODO handleErrorResponse()
    }

    private fun showProgressBar() {
//        TODO showProgressBar()
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