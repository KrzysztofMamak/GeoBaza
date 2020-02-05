package com.mamak.geobaza.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.*
import com.google.android.material.navigation.NavigationView
import com.google.firebase.messaging.FirebaseMessaging
import com.mamak.geobaza.R
import com.mamak.geobaza.ui.base.BaseThemeActivityActionBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseThemeActivityActionBar(),
        NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseMessaging.getInstance().subscribeToTopic("/topics/projects")
        setContentView(R.layout.activity_main)
        setNavigation()
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
            R.id.nav_travel_planner -> {}
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
            R.id.nav_sign_out -> {
                Navigation.findNavController(this, R.id.nav_host_fragment)
                    .navigate(R.id.multipleChoiceDialogFragment)
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
}