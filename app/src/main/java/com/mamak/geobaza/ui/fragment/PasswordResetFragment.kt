package com.mamak.geobaza.ui.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.ImageViewCompat
import androidx.lifecycle.Observer
import com.mamak.geobaza.factory.ViewModelFactory
import com.mamak.geobaza.network.firebase.GeoBazaException
import com.mamak.geobaza.network.firebase.GeoBazaException.ErrorCode.ERROR_USER_DISABLED
import com.mamak.geobaza.network.firebase.GeoBazaException.ErrorCode.ERROR_USER_NOT_FOUND
import com.mamak.geobaza.base.BaseFragment
import com.mamak.geobaza.utils.manager.ValidationManager
import com.mamak.geobaza.utils.view.EmptyView
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_reset_password.*
import javax.inject.Inject
import com.mamak.geobaza.R
import com.mamak.geobaza.viewmodel.PasswordResetViewModel
import com.mamak.geobaza.utils.manager.ThemeManager
import com.mamak.geobaza.utils.manager.KeyboardManager

class PasswordResetFragment : BaseFragment() {
    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
    @Inject
    internal lateinit var passwordResetViewModel: PasswordResetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reset_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClick()
        setEmptyView()
        setListener()
    }

    private fun initViewModel() {
        passwordResetViewModel = viewModelFactory.create(PasswordResetViewModel::class.java)
    }

    private fun setOnClick() {
        b_password_reset.setOnClickListener {
            resetFeedbackInfo()
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
        et_email.apply {
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    setPasswordResetButton()
                }
            })
            setOnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    KeyboardManager.hideSoftKeyboard(context, v)
                    if (validateEmail()) {
                        resetPassword()
                    }
                    true
                } else {
                    false
                }
            }
        }
    }

    private fun validateEmail(): Boolean {
        val email = et_email.text.toString()
        return ValidationManager.validateEmail(email)
    }

    private fun resetPassword() {
        val email = et_email.text.toString()
        sendPasswordResetLink(email)
    }

    private fun sendPasswordResetLink(email: String) {
        passwordResetViewModel.apply {
            resetPassword(email)
            getResetPasswordLiveData().observe(
                this@PasswordResetFragment, Observer { resource ->
                    when {
                        resource.isLoading -> pb_forgot_password.visibility = View.VISIBLE
                        resource.isSuccess -> handlePasswordResetSuccessResponse()
                        else -> handlePasswordResetErrorResponse(resource.exception)
                    }
                }
            )
        }
    }

    private fun handlePasswordResetSuccessResponse() {
        pb_forgot_password.visibility = View.GONE
        tv_feedback.apply {
            text = getString(R.string.password_reset_success)
            visibility = View.VISIBLE
        }
        showIconByFeedback(true)
    }

    private fun handlePasswordResetErrorResponse(geoBazaException: GeoBazaException?) {
        when (geoBazaException?.errorCode) {
            GeoBazaException.FIREBASE_AUTH_INVALID_USER_EXCEPTION -> {
                when (geoBazaException.internalErrorCode) {
                    ERROR_USER_DISABLED -> {
                        tv_feedback.text = getString(R.string.password_reset_user_disabled)
                    }
                    ERROR_USER_NOT_FOUND -> {
                        tv_feedback.text = getString(R.string.password_reset_user_not_found)
                    }
                }
            }
            GeoBazaException.FIREBASE_EXCEPTION -> {
                tv_feedback.text = getString(R.string.password_reset_connection_failed)
            }
            else -> {
                tv_feedback.text = getString(R.string.something_went_wrong)
            }
        }
        pb_forgot_password.visibility = View.GONE
        tv_feedback.visibility = View.VISIBLE
        showIconByFeedback(false)
    }

    private fun setPasswordResetButton() {
        val color: Int
        val drawable: Int

        if (validateEmail()) {
            color = ThemeManager.getColorResByAttr(activity, R.attr.colorTextOnSecondary)
            drawable = R.drawable.item_circle_full
        } else {
            color = ThemeManager.getColorResByAttr(activity, R.attr.colorSecondaryLight)
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
                ColorStateList.valueOf(context.getColor(
                    ThemeManager.getColorResByAttr(activity, R.attr.colorSecondaryLight)
                ))
            )
            visibility = View.VISIBLE
        }
    }

//    TODO Fix
    private fun resetFeedbackInfo() {
        iv_forgot_password_check.visibility = View.GONE
        tv_feedback.visibility = View.GONE
    }
}