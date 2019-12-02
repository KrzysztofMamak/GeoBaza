package com.mamak.geobaza.ui.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ImageViewCompat
import androidx.lifecycle.Observer
import com.mamak.geobaza.R
import com.mamak.geobaza.factory.ViewModelFactory
import com.mamak.geobaza.ui.base.BaseFragment
import com.mamak.geobaza.ui.viewmodel.RegistrationLoginSharedViewModel
import com.mamak.geobaza.utils.manager.ValidationManager
import com.mamak.geobaza.utils.view.EmptyView
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
        setEmptyView()
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
        resetFeedbackInfo()
        showProgressBar()
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
                        handleErrorResponse(resource.exception)
                    }
                }
            )
    }

    private fun setEmptyView() {
        ev_no_password.apply {
            draw(EmptyView.Type.NO_PASSWORD)
            visibility = View.VISIBLE
        }
    }

    private fun showProgressBar() {
        pb_forgot_password_registration.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        pb_forgot_password_registration.visibility = View.GONE
    }

    private fun handleSuccessResponse() {
        hideProgressBar()
        tv_feedback.apply {
            text = getString(R.string.password_reset_success)
            visibility = View.VISIBLE
        }
        showIconForSuccess()
    }

    private fun handleErrorResponse(exception: Exception? = null) {
        hideProgressBar()
        tv_feedback.apply {
            text = getString(R.string.password_reset_failed)
            visibility = View.VISIBLE
        }
        showIconForFail()
    }

    private fun resetFeedbackInfo() {
        iv_forgot_password_check.visibility = View.GONE
        tv_feedback.visibility = View.GONE
    }

//    TODO refactor
    private fun showIconForSuccess() {
        iv_forgot_password_check.apply {
            setImageDrawable(context.getDrawable(R.drawable.ic_check))
            ImageViewCompat.setImageTintList(
                this,
                ColorStateList.valueOf(context.getColor(R.color.colorSecondaryLight)))
            visibility = View.VISIBLE
        }
    }

//    TODO refactor
    private fun showIconForFail() {
        iv_forgot_password_check.apply {
            setImageDrawable(context.getDrawable(R.drawable.ic_cross))
            ImageViewCompat.setImageTintList(
                this,
                ColorStateList.valueOf(context.getColor(R.color.colorSecondaryLight)))
            visibility = View.VISIBLE
        }
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

//    TODO refactor
    private fun allowPasswordReset() {
        b_reset_password.apply {
            context?.let {
                background = it.getDrawable(R.drawable.item_circle_full)
                setTextColor(it.getColor(R.color.colorTextOnSecondary))
                isEnabled = true
            }
        }
    }

//    TODO refactor
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