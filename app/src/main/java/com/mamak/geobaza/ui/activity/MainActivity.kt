package com.mamak.geobaza.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.mamak.geobaza.R
import com.mamak.geobaza.ui.base.BaseThemeActivityNoActionBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.drawer_layout

class MainActivity : BaseThemeActivityNoActionBar(),
        NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavigation()
    }

    private fun initNavigation() {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawer_layout)
        NavigationUI.setupWithNavController(nv_main, navController)
        nv_main.setNavigationItemSelectedListener(this)
    }

    private fun setNavigation() {
        setSupportActionBar(tb_main)
        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            tb_main,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nv_main.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_travel_planner -> {}
            R.id.nav_project_list -> {
                Navigation.findNavController(this, R.id.nav_host_fragment)
                    .navigate(R.id.fragment_project_list)
            }
            R.id.nav_statistics -> {

            }
            R.id.nav_setting -> {
                Navigation.findNavController(this, R.id.nav_host_fragment)
                        .navigate(R.id.fragment_settings)
            }
            R.id.nav_sign_out -> {}
        }
        item.isChecked = true
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.project_list_actionbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_locate -> {

            }
            R.id.action_filter -> {

            }
            R.id.action_search -> {

            }
        }
        return true
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}