package com.murali.taskmanager.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.murali.taskmanager.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setCurrentFragment(HomeFragment())

        bottomNavBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.page_1 -> setCurrentFragment(HomeFragment())
                R.id.page_2 -> setCurrentFragment(TaskFragment())
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.framelayout_container, fragment)
                .commit()
        }

}