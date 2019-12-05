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
import timber.log.Timber
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
        b_password_reset.setOnClickListener {
            resetPassword()
        }
    }

    private fun setEmptyView() {
        ev_no_password.apply {
            draw(EmptyView.Type.NO_PASSWORD)
            visibility = View.VISIBLE
        }
    }

    private fun setListener() {
        et_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setPasswordResetButton()
            }
        })
    }

    private fun resetPassword() {
        val email = et_email.text.toString()
        resetFeedbackInfo()
        sendPasswordResetLink(email)
    }

    private fun sendPasswordResetLink(email: String) {
        registrationLoginSharedViewModel.resetPassword(email)
        registrationLoginSharedViewModel.getResetPasswordLiveData()
            .observe(
                this, Observer { resource ->
                    when {
                        resource.isLoading -> pb_forgot_password.visibility = View.VISIBLE
                        resource.isSuccess -> handlePasswordResetSuccessResponse()
                        else -> handlePasswordResetErrorResponse(resource.exception)
                    }
                }
            )
    }

    private fun handlePasswordResetSuccessResponse() {
        pb_forgot_password.visibility = View.GONE
        tv_feedback.apply {
            text = getString(R.string.password_reset_success)
            visibility = View.VISIBLE
        }
        showIconByFeedback(true)
    }

    private fun handlePasswordResetErrorResponse(exception: Exception? = null) {
        Timber.e(exception)
        pb_forgot_password.visibility = View.GONE
        tv_feedback.apply {
            text = getString(R.string.password_reset_failed)
            visibility = View.VISIBLE
        }
        showIconByFeedback(false)
    }

    private fun resetFeedbackInfo() {
        iv_forgot_password_check.visibility = View.GONE
        tv_feedback.visibility = View.GONE
    }

    private fun setPasswordResetButton() {
        val color: Int
        val drawable: Int

        if (validateEmail()) {
            color = R.color.colorTextOnSecondary
            drawable = R.drawable.item_circle_full
        } else {
            color = R.color.colorSecondaryLight
            drawable = R.drawable.item_circle
        }

        b_password_reset.apply {
            context?.let {
                background = it.getDrawable(drawable)
                setTextColor(it.getColor(color))
                isEnabled = true
            }
        }
    }

    private fun showIconByFeedback(isSuccessful: Boolean) {
        val drawable = if (isSuccessful) R.drawable.ic_check else R.drawable.ic_cross

        iv_forgot_password_check.apply {
            setImageDrawable(context.getDrawable(drawable))
            ImageViewCompat.setImageTintList(
                this,
                ColorStateList.valueOf(context.getColor(R.color.colorSecondaryLight)))
            visibility = View.VISIBLE
        }
    }

    private fun validateEmail(): Boolean {
        val email = et_email.text.toString()
        if (ValidationManager.validateEmail(email)) {
            return true
        }
        return false
    }

    private fun initViewModel() {
        registrationLoginSharedViewModel = viewModelFactory.create(RegistrationLoginSharedViewModel::class.java)
    }
}