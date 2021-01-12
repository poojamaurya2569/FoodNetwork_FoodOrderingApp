package com.example.foodnetwork.Activites

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.room.Room
import com.example.foodnetwork.NavFragments.*
import com.example.foodnetwork.R
import com.example.foodnetwork.database.ResDatabase
import com.example.foodnetwork.database.ResEntities
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var navigationView: NavigationView
    lateinit var frameLayout: FrameLayout
    lateinit var drawerLayout: DrawerLayout



    var previousMenuItem: MenuItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        toolbar = findViewById(R.id.toolbar)
        navigationView = findViewById(R.id.navigationHeader)
        frameLayout = findViewById(R.id.frameLayout)
        drawerLayout = findViewById(R.id.drawerLayout)

        setUpToolbsr()
        onBackPressed()
        val actionBarDrawerToggle = ActionBarDrawerToggle(this@MainActivity,
            drawerLayout,R.string.open_drawer,R.string.close_drawer)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {

            if (previousMenuItem != null)
            {
                previousMenuItem?.isChecked = false
            }
            it.isCheckable = true
            it.isChecked = true
            previousMenuItem = it

            when(it.itemId){
                R.id.home->{
                    OpenHome()
                    drawerLayout.closeDrawers()
                }
                R.id.myProfile->{
                    supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout,MyProfile()).commit()
                    supportActionBar?.title = "My Profile"
                    drawerLayout.closeDrawers()}
                R.id.orderHistory->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout,OrderHistory()).commit()
                    supportActionBar?.title = "Order History"
                    drawerLayout.closeDrawers()
                }
                R.id.favouriteRestaurants->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout,FavouriteRestaurants()).commit()
                    supportActionBar?.title = "Favourite Restaurants"
                    drawerLayout.closeDrawers()
                }
                R.id.FAQ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout,FAQuestions()).commit()
                    supportActionBar?.title = "Frequantly Asked Questions"
                    drawerLayout.closeDrawers()
                }
                R.id.logOut->{
                    val builder = AlertDialog.Builder(this@MainActivity)
                    builder.setTitle("Confirmation")
                        .setMessage("Are you sure you want exit?")
                        .setPositiveButton("Yes") { _, _ ->
                            Intent(this@MainActivity, LoginPage::class.java)
                        }
                        .setNegativeButton("No") { _, _ ->
                            OpenHome()
                        }
                        .create()
                        .show()

                }
            }
            return@setNavigationItemSelectedListener true
        }


    }
    fun setUpToolbsr()
    {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Home"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
    fun OpenHome()
    {
        val transaction = supportFragmentManager.beginTransaction()
           transaction.replace(R.id.frameLayout,HomeFregment()).commit()
        supportActionBar?.title = "Home"
        navigationView.setCheckedItem(R.id.home)
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.frameLayout)
        if (fragment != HomeFregment())
        {
            OpenHome()
        }
        else
        {super.onBackPressed()}
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }


}
