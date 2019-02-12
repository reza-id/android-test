package com.reza.testingexample.home.detail

import android.database.sqlite.SQLiteConstraintException
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.reza.testingexample.R
import com.reza.testingexample.data.api.ApiRepository
import com.reza.testingexample.data.db.FavoriteCountry
import com.reza.testingexample.data.db.database
import com.reza.testingexample.data.model.Country
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class CountryDetailActivity : AppCompatActivity(), CountryDetailView {
    private lateinit var presenter: CountryDetailPresenter
    private lateinit var country: Country
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private lateinit var countryFlag: ImageView
    private lateinit var countryName: TextView
    private lateinit var countryCapitalCity: TextView
    private lateinit var countryRegion: TextView
    private lateinit var countrySubRegion: TextView

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var code: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        code = intent.getStringExtra("code")
        supportActionBar?.title = "Country Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            backgroundColor = Color.WHITE

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                scrollView {
                    isVerticalScrollBarEnabled = false
                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(10)
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER_HORIZONTAL

                            countryFlag = imageView {}.lparams(height = dip(75))

                            countryName = textView {
                                this.gravity = Gravity.CENTER
                                textSize = 20f
                                textColor = ContextCompat.getColor(context, R.color.colorAccent)
                            }.lparams {
                                topMargin = dip(5)
                            }

                            countryCapitalCity = textView {
                                this.gravity = Gravity.CENTER
                            }

                            countryRegion = textView {
                                this.gravity = Gravity.CENTER
                                textColor = ContextCompat.getColor(context, R.color.colorPrimaryText)
                            }.lparams {
                                topMargin = dip(20)
                            }

                            countrySubRegion = textView {
                                this.gravity = Gravity.CENTER
                            }
                        }
                        progressBar = progressBar {
                        }.lparams {
                            centerHorizontally()
                        }
                    }
                }
            }
        }

        favoriteState()
        val request = ApiRepository()
        val gson = Gson()
        presenter = CountryDetailPresenter(this, request, gson)
        presenter.getCountryDetail(code)

        swipeRefresh.onRefresh {
            presenter.getCountryDetail(code)
        }
    }

    private fun favoriteState() {
        database.use {
            val result = select(FavoriteCountry.TABLE_FAVORITE)
                    .whereArgs("(COUNTRY_CODE = {code})",
                            "code" to code)
            val favorite = result.parseList(classParser<FavoriteCountry>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun showCountryDetail(data: Country) {
        country = data
        swipeRefresh.isRefreshing = false
        Glide.with(this)
                .load("https://www.countryflags.io/${country.alpha2Code}/flat/64.png")
                .into(countryFlag)
        countryName.text = data.name
        countryCapitalCity.text = "Capital City: " + data.capital
        countryRegion.text = "Region: " + data.region
        countrySubRegion.text = "Sub-region" + data.subregion

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(FavoriteCountry.TABLE_FAVORITE,
                        FavoriteCountry.COUNTRY_CODE to country.alpha2Code,
                        FavoriteCountry.COUNTRY_NAME to country.name,
                        FavoriteCountry.CAPITAL_CITY to country.capital)
            }
            swipeRefresh.snackbar("Added to favorite").show()
        } catch (e: SQLiteConstraintException) {
            swipeRefresh.snackbar(e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(FavoriteCountry.TABLE_FAVORITE, "(COUNTRY_CODE = {code})",
                        "code" to code)
            }
            swipeRefresh.snackbar("Removed to favorite").show()
        } catch (e: SQLiteConstraintException) {
            swipeRefresh.snackbar(e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }
}