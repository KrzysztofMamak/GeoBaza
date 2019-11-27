package com.mamak.geobaza.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.mamak.geobaza.R
import com.mamak.geobaza.factory.ViewModelFactory
import com.mamak.geobaza.ui.base.BaseFragment
import com.mamak.geobaza.ui.viewmodel.RegistrationLoginSharedViewModel
import com.mamak.geobaza.utils.manager.ValidationManager
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_forgot_password.*
import javax.inject.Inject

class PasswordResetFragment : BaseFragment() {
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
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClick()
        setListener()
    }

    private fun setOnClick() {
        b_reset_password.setOnClickListener {
            resetPassword()
        }
    }

    private fun setListener() {
        et_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setPasswordResetAvailability()
            }
        })
    }

    private fun resetPassword() {
        val email = et_email.text.toString()
        sendPasswordResetLink(email)
    }

    private fun sendPasswordResetLink(email: String) {
        registrationLoginSharedViewModel.resetPassword(email)
        registrationLoginSharedViewModel.getResetPasswordLiveData()
            .observe(
                this, Observer { resource ->
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
                }
            )
    }

    private fun showProgressBar() {
//        TODO showProgressBar
    }

    private fun handleSuccessResponse() {
//        TODO handleSuccessResponse
    }

    private fun handleErrorResponse() {
//        TODO handleErrorResponse
    }

    private fun setPasswordResetAvailability() {
        if (validateEmail()) {
            allowPasswordReset()
        } else {
            denyPasswordReset()
        }
    }

    private fun validateEmail(): Boolean {
        val email = et_email.text.toString()
        if (ValidationManager.validateEmail(email)) {
            return true
        }
        return false
    }

    private fun allowPasswordReset() {
        b_reset_password.apply {
            context?.let {
                background = it.getDrawable(R.drawable.item_circle_full)
                setTextColor(it.getColor(R.color.colorTextOnSecondary))
                isEnabled = true
            }
        }
    }

    private fun denyPasswordReset() {
        b_reset_password.apply {
            context?.let {
                background = it.getDrawable(R.drawable.item_circle)
                setTextColor(it.getColor(R.color.colorSecondaryLight))
                isEnabled = false
            }
        }
    }

    private fun initViewModel() {
        registrationLoginSharedViewModel = viewModelFactory.create(RegistrationLoginSharedViewModel::class.java)
    }
}