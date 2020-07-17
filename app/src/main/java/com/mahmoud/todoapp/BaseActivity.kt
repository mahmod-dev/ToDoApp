package com.mahmoud.todoapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.mahmoud.todoapp.fragments.EventsFragment
import com.mahmoud.todoapp.fragments.HomeFragment
import com.mahmoud.todoapp.fragments.ProfileFragment
import com.mahmoud.todoapp.fragments.TasksFragment
import com.mahmoud.todoapp.util.Constants
import com.mahmoud.todoapp.util.LocaleHelper
import com.mahmoud.todoapp.util.MyPreferences
import com.yariksoffice.lingver.Lingver
import kotlinx.android.synthetic.main.activity_base.*
import java.util.*

open class BaseActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {
    private var mCurrentLocale: Locale? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        MyPreferences.context = this
        //MyPreferences.setStr(Constants.LANGUAGE, Constants.ARABIC)


        btnLocale.setOnClickListener {
            if (MyPreferences.getStr(Constants.LANGUAGE).equals(Constants.ENGLISH)){
                MyPreferences.setStr(Constants.LANGUAGE, Constants.ARABIC)
                LocaleHelper.setNewLocale(this, MyPreferences.getStr(Constants.LANGUAGE)!!)

            }else{
                MyPreferences.setStr(Constants.LANGUAGE, Constants.ENGLISH)
                LocaleHelper.setNewLocale(this, MyPreferences.getStr(Constants.LANGUAGE)!!)
            }

        }

        nav_view.setNavigationItemSelectedListener(this@BaseActivity)
        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState()
        ///////////////////

        fabTask.setOnClickListener {
            val intent = Intent(this@BaseActivity, AddTasksActivity::class.java)
            startActivity(intent)
        }

        fabEvent.setOnClickListener {
            val intent = Intent(this@BaseActivity, AddEventsActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuHome -> supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                HomeFragment()
            ).commit()
            R.id.menuEvents -> supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                EventsFragment()
            ).commit()
            R.id.menuTasks -> supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                TasksFragment()
            ).commit()
            R.id.menuProfile -> supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                ProfileFragment()
            ).commit()

        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}
