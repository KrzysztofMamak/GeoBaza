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
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import com.mamak.geobaza.R
import com.mamak.geobaza.factory.ViewModelFactory
import com.mamak.geobaza.ui.activity.ProjectListActivity
import com.mamak.geobaza.ui.base.BaseFragment
import com.mamak.geobaza.ui.viewmodel.RegistrationLoginSharedViewModel
import com.mamak.geobaza.utils.constans.AppConstans.REQUEST_CODE_SIGN_IN_VIA_GOOGLE
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
        setForgotPasswordView()
        setListeners()
    }

    private fun setOnClicks() {
        b_login_email.setOnClickListener {
            signInViaEmailAndPassword()
        }

        b_login_google.setOnClickListener {
            signInViaGoogle()
        }

        b_login_github.setOnClickListener {
//            TODO loginViaGithub
        }

        tv_register.setOnClickListener {
            launchRegistrationFragment()
        }

        tv_forgot_password.setOnClickListener {
//            TODO forgotPassword
        }
    }

    private fun setForgotPasswordView() {
        tv_forgot_password.text = getText(R.string.forgot_password)
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

    private fun signInViaEmailAndPassword() {
        val email = et_email.text.toString()
        val password = et_password.text.toString()
        authViaEmailAndPassword(email, password)
    }

    private fun authViaEmailAndPassword(email: String, password: String) {
        registrationLoginSharedViewModel.authViaEmailAndPassword(email, password)
        registrationLoginSharedViewModel.getAuthViaEmailLiveData().observe(
            this, Observer { resource ->
                if (resource.isLoading) {
                    showProgressBar()
                } else if (resource.data != null) {
                    if (resource.data.isSuccessful) {
                        startProjectListActivity()
                    } else {
                        handleErrorResponse()
                    }
                } else {
                    handleErrorResponse()
                }
            }
        )
    }

    private fun signInViaGoogle() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(activity, googleSignInOptions)

        val intent = googleSignInClient.signInIntent
        startActivityForResult(intent, REQUEST_CODE_SIGN_IN_VIA_GOOGLE)
    }

    private fun authViaGoogle(googleSignInAccount: GoogleSignInAccount?) {
        registrationLoginSharedViewModel.authViaGoogle(
            GoogleAuthProvider.getCredential(googleSignInAccount?.idToken, null)
        )
        registrationLoginSharedViewModel.getAuthViaGoogleLiveData().observe(
            this, Observer { resource ->
                if (resource.isLoading) {
                    showProgressBar()
                } else if (resource.data != null) {
                    if (resource.data.isSuccessful) {
                        startProjectListActivity()
                    } else {
                        handleErrorResponse()
                    }
                } else {
                    handleErrorResponse()
                }
            }
        )
    }

    private fun setLoginAvailability() {
        if (validateUser()) {
            allowSignIn()
        } else {
            denySignIn()
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

    private fun allowSignIn() {
        b_login_email.apply {
            context?.let {
                background = it.getDrawable(R.drawable.item_circle_full)
                setTextColor(it.getColor(R.color.colorTextOnSecondary))
                isEnabled = true
            }
        }
    }

    private fun denySignIn() {
        b_login_email.apply {
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

    private fun startProjectListActivity() {
        val intent = Intent(activity, ProjectListActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        activity.finish()
    }

    private fun initViewModel() {
        registrationLoginSharedViewModel = viewModelFactory.create(RegistrationLoginSharedViewModel::class.java)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SIGN_IN_VIA_GOOGLE) {
            val googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (googleSignInResult.isSuccess) {
                authViaGoogle(googleSignInResult.signInAccount)
            }
        }
    }
}