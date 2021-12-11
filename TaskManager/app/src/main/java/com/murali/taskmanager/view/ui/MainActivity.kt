package com.murali.taskmanager.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.murali.taskmanager.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setting default fragment as home
        setCurrentFragment(HomeFragment())

        //fragment navigation
        bottomNavBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> setCurrentFragment(HomeFragment())
                R.id.task -> setCurrentFragment(TaskDeleteFragment())
            }
            true
        }

    }

    //fragment transaction
    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.framelayout_container, fragment)
                .commit()
        }

}