package com.mamak.geobaza.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.Observer
import com.mamak.geobaza.R
import com.mamak.geobaza.factory.ViewModelFactory
import com.mamak.geobaza.ui.base.BaseFragment
import com.mamak.geobaza.ui.viewmodel.RegistrationLoginSharedViewModel
import com.mamak.geobaza.utils.constans.AppConstans.DELAY_SHORT
import com.mamak.geobaza.utils.manager.ValidationManager
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
        setListeners()
    }

    private fun setOnClick() {
        b_register.setOnClickListener {
            register()
        }
    }

    private fun setListeners() {
        listOf<EditText>(et_email, et_password, et_password_confirm).forEach {
            it.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    setRegistrationButton()
                }
            })
        }
    }

    private fun validateUser(): Boolean {
        val password = et_password.text.toString()
        val passwordConfirm = et_password_confirm.text.toString()
        val email = et_email.text.toString()
        if (ValidationManager.validateRegistrationData(email, password, passwordConfirm)) {
            return true
        }
        return false
    }

    private fun register() {
        val email = et_email.text.toString()
        val password = et_password.text.toString()
        registrationLoginSharedViewModel.registerViaEmailAndPassword(email, password)
        registrationLoginSharedViewModel.getRegistrationLiveData().observe(this, Observer { resource ->
            when {
                resource.isLoading -> showProgressBar()
                resource.isSuccess -> handleSuccessResponse()
                else -> handleErrorResponse()
            }
        })
    }

    private fun setRegistrationButton() {
        val color: Int
        val drawable: Int

        if (validateUser()) {
            color = R.color.colorTextOnSecondary
            drawable = R.drawable.item_circle_full
        } else {
            color = R.color.colorSecondaryLight
            drawable = R.drawable.item_circle
        }

        b_register.apply {
            context?.let {
                setTextColor(it.getColor(color))
                background = it.getDrawable(drawable)
                isEnabled = true
            }
        }
    }

    private fun handleSuccessResponse() {
        hideProgressBar()
        iv_registration_check.visibility = View.VISIBLE
        Handler().postDelayed({
            launchLoginFragment()
        }, DELAY_SHORT)
    }

    private fun handleErrorResponse() {}

    private fun showProgressBar() {
        pb_registration.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        pb_registration.visibility = View.GONE
    }

    private fun launchLoginFragment() {
        fragmentManager?.popBackStack()
    }

    private fun initViewModel() {
        registrationLoginSharedViewModel = viewModelFactory.create(RegistrationLoginSharedViewModel::class.java)
    }
}