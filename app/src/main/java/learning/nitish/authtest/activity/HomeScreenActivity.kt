/*
 * Created by Nitish Singh on 29/7/17 4:40 AM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 29/7/17 4:39 AM
 */

package learning.nitish.authtest.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import learning.nitish.authtest.R
import learning.nitish.authtest.fragments.RxJavaFragment
import learning.nitish.authtest.fragments.TweeterUIFragment

/**
 * Created by Nitish Singh Rathore on 29/7/17.
 */

/**
 * Base Activity class to contain the fragments
 */
class HomeScreenActivity : AppCompatActivity() {

    private var mBottomNavigation: BottomNavigationView? = null
    private var fragment: Fragment? = null
    private var fragmentManager: FragmentManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        mBottomNavigation = findViewById(R.id.navigation) as BottomNavigationView

        fragmentManager = supportFragmentManager

        mBottomNavigation!!.menu.findItem(R.id.navigation_tui).isChecked = true
        fragment = TweeterUIFragment()
        val transaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.container, fragment).commit()
        mBottomNavigation!!.setOnNavigationItemSelectedListener { item ->
            val id = item.itemId


            when (id) {
                R.id.navigation_tui -> fragment = TweeterUIFragment()
                R.id.navigation_rxj -> fragment = RxJavaFragment()

                else -> fragment = TweeterUIFragment()
            }

            val transaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.container, fragment).commit()
            true
        }

    }
}
