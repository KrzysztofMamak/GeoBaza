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
import com.mamak.geobaza.R
import com.mamak.geobaza.factory.ViewModelFactory
import com.mamak.geobaza.network.firebase.GeoBazaException
import com.mamak.geobaza.network.firebase.GeoBazaException.ErrorCode.FIREBASE_AUTH_USER_COLLISION_EXCEPTION
import com.mamak.geobaza.network.firebase.GeoBazaException.ErrorCode.FIREBASE_EXCEPTION
import com.mamak.geobaza.ui.base.BaseFragment
import com.mamak.geobaza.ui.viewmodel.RegistrationLoginSharedViewModel
import com.mamak.geobaza.utils.manager.KeyboardManager
import com.mamak.geobaza.utils.manager.ThemeManager
import com.mamak.geobaza.utils.manager.ValidationManager
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlinx.android.synthetic.main.fragment_registration.et_email
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

    private fun initViewModel() {
        registrationLoginSharedViewModel = viewModelFactory.create(RegistrationLoginSharedViewModel::class.java)
    }

    private fun setOnClick() {
        b_register.setOnClickListener {
            resetFeedbackInfo()
            register()
        }
    }

    private fun setListeners() {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setRegistrationButton()
            }
        }

        et_email.addTextChangedListener(textWatcher)
        et_password.addTextChangedListener(textWatcher)
        et_password_confirm.apply {
            addTextChangedListener(textWatcher)
            setOnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    KeyboardManager.hideSoftKeyboard(context, v)
                    if (validateUser()) {
                        register()
                    }
                    true
                } else {
                    false
                }
            }
        }
    }

    private fun validateUser(): Boolean {
        val password = et_password.text.toString()
        val passwordConfirm = et_password_confirm.text.toString()
        val email = et_email.text.toString()
        return (ValidationManager.validateRegistrationData(email, password, passwordConfirm))
    }

    private fun register() {
        val email = et_email.text.toString()
        val password = et_password.text.toString()
        registrationLoginSharedViewModel.apply {
            registerViaEmailAndPassword(email, password)
            getRegistrationLiveData().observe(this@RegistrationFragment, Observer { resource ->
                when {
                    resource.isLoading -> pb_registration.visibility = View.VISIBLE
                    resource.isSuccess -> handleRegistrationSuccessResponse()
                    else -> handleRegistrationErrorResponse(resource.exception)
                }
            })
        }
    }

    private fun handleRegistrationSuccessResponse() {
        pb_registration.visibility = View.GONE
        tv_feedback.apply {
            text = context?.getString(R.string.account_created)
            visibility = View.VISIBLE
        }
        showIconByFeedback(true)
    }

    private fun handleRegistrationErrorResponse(geoBazaException: GeoBazaException?) {
        if (geoBazaException != null) {
            when (geoBazaException.errorCode) {
                FIREBASE_AUTH_USER_COLLISION_EXCEPTION -> {
                    tv_feedback.text = context?.getString(R.string.user_exists)
                }
                FIREBASE_EXCEPTION -> {
                    tv_feedback.text = context?.getString(R.string.account_no_connection)
                }
            }
        } else {
            tv_feedback.text = getString(R.string.something_went_wrong)
        }
        pb_registration.visibility = View.GONE
        tv_feedback.visibility = View.VISIBLE
        showIconByFeedback(false)
    }

    private fun setRegistrationButton() {
        val color: Int
        val drawable: Int

        if (validateUser()) {
            color = ThemeManager.getColorResByAttr(activity, R.attr.colorTextOnSecondary)
            drawable = R.drawable.item_circle_full
        } else {
            color = ThemeManager.getColorResByAttr(activity, R.attr.colorSecondaryLight)
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

    private fun showIconByFeedback(isSuccessful: Boolean) {
        val drawable = if (isSuccessful) R.drawable.ic_check else R.drawable.ic_cross

        iv_registration_check.apply {
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
        iv_registration_check.visibility = View.GONE
        tv_feedback.visibility = View.GONE
    }
}