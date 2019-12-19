package com.mamak.geobaza.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.mamak.geobaza.R
import com.mamak.geobaza.ui.base.BaseThemeActivityNoActionBar
import com.mamak.geobaza.ui.fragment.LoginFragment

class EntryActivity : BaseThemeActivityNoActionBar() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)
        replaceFragment(LoginFragment())
    }

    override fun onStart() {
        super.onStart()
        checkUserSession()
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
        val intent = Intent(this, MainActivity::class.java)
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
            statusBarColor = getColor(R.color.colorPrimaryDark)
        }
    }
}