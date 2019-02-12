package com.reza.testingexample.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.reza.testingexample.R
import com.reza.testingexample.R.id.countries
import com.reza.testingexample.R.id.favorites
import com.reza.testingexample.home.countries.CountriesFragment
import com.reza.testingexample.home.favorites.FavoriteCountriesFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                countries -> {
                    loadCountriesFragment(savedInstanceState)
                }
                favorites -> {
                    loadFavoritesFragment(savedInstanceState)
                }
            }
            true
        }
        bottom_navigation.selectedItemId = countries
    }


    private fun loadCountriesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, CountriesFragment(), CountriesFragment::class.simpleName)
                    .commit()
        }
    }

    private fun loadFavoritesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, FavoriteCountriesFragment(), FavoriteCountriesFragment::class.simpleName)
                    .commit()
        }
    }
}