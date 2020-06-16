package com.mamak.geobaza.ui.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.*
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.mamak.geobaza.R
import com.mamak.geobaza.factory.ViewModelFactory
import com.mamak.geobaza.base.BaseThemeActivityActionBar
import com.mamak.geobaza.viewmodel.MainActivityViewModel
import com.mamak.geobaza.utils.manager.DialogManager
import com.mamak.geobaza.utils.manager.GoogleSignInManager
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseThemeActivityActionBar(),
        NavigationView.OnNavigationItemSelectedListener {
    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory
    @Inject
    internal lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        initViewModel()
        FirebaseMessaging.getInstance().subscribeToTopic("/topics/projects")
        setContentView(R.layout.activity_main)
        setNavigation()
    }

    private fun initViewModel() {
        mainActivityViewModel = viewModelFactory.create(mainActivityViewModel::class.java)
    }

    private fun setNavigation() {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        setupActionBarWithNavController(this, navController, drawer_layout)
        setupWithNavController(nv_main, navController)
        nv_main.setNavigationItemSelectedListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            return if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawer_layout.closeDrawer(GravityCompat.START)
                true
            } else {
                false
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_travel_planner -> {
                Navigation.findNavController(this, R.id.nav_host_fragment)
                    .navigate(R.id.travelPlannerFragment)
            }
            R.id.nav_project_list -> {
                Navigation.findNavController(this, R.id.nav_host_fragment)
                    .navigate(R.id.projectListFragment)
            }
            R.id.nav_statistics -> {
                Navigation.findNavController(this, R.id.nav_host_fragment)
                    .navigate(R.id.statisticsFragment)
            }
            R.id.nav_setting -> {
                Navigation.findNavController(this, R.id.nav_host_fragment)
                    .navigate(R.id.settingsFragment)
            }
            R.id.nav_map -> {
                Navigation.findNavController(this, R.id.nav_host_fragment)
                    .navigate(R.id.mapFragment)
            }
            R.id.nav_sign_out -> {
//                TODO change to navigation component
                showSignOutConfirmDialog()
            }
        }
        item.isChecked = true
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return navigateUp(Navigation.findNavController(this, R.id.nav_host_fragment), drawer_layout)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_project_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun signOut() {
        val googleSignInClient = GoogleSignInManager.getGoogleSignInClient(this,
            getString(R.string.default_web_client_id))
        FirebaseAuth.getInstance().signOut()
        mainActivityViewModel.apply {
            googleSignOut(googleSignInClient)
            getGoogleSignOutLiveData().observe(
                this@MainActivity, Observer { resource ->
                    when {
                        resource.isLoading -> {}
                        resource.isSuccess -> {
                            launchEntryActivity()
                        }
                        else -> {}
                    }
                }
            )
        }
    }

    private fun showSignOutConfirmDialog() {
        DialogManager.showYesNoDialog(
            context = this,
            description = getString(R.string.sign_out_confirm),
            textYes = getString(R.string.yes),
            textNo = getString(R.string.no),
            dialogOnClickListener = DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        dialog.dismiss()
                        signOut()
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        dialog.dismiss()
                    }
                }
            }
        )
    }

    private fun launchEntryActivity() {
        val intent = Intent(this, EntryActivity::class.java)
        startActivity(intent)
        this.finish()
    }
}