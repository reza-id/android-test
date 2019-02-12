package com.reza.testingexample.home.countries

import android.R
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.gson.Gson
import com.reza.testingexample.R.array.regions
import com.reza.testingexample.R.color.colorAccent
import com.reza.testingexample.data.api.ApiRepository
import com.reza.testingexample.data.model.Country
import com.reza.testingexample.home.detail.CountryDetailActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class CountriesFragment : Fragment(), AnkoComponent<Context>, CountriesView {

    private var countries: MutableList<Country> = mutableListOf()
    private lateinit var presenter: CountriesPresenter
    private lateinit var adapter: CountriesAdapter
    private lateinit var spinner: Spinner
    private lateinit var listCountry: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var regionCode: String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val spinnerItems = resources.getStringArray(regions)
        val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        adapter = CountriesAdapter(countries) {
            context?.startActivity<CountryDetailActivity>("code" to "${it.alpha2Code}")
        }
        listCountry.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = CountriesPresenter(this, request, gson)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                regionCode = spinner.selectedItem.toString()
                presenter.getCountriesByRegion(regionCode)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        swipeRefresh.onRefresh {
            presenter.getCountriesByRegion(regionCode)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(requireContext()))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            spinner = spinner()
            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                        colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light
                )

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    listCountry = recyclerView {
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progressBar = progressBar {
                    }.lparams {
                        centerHorizontally()
                    }
                }
            }
        }
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun showCountryList(data: List<Country>) {
        swipeRefresh.isRefreshing = false
        countries.clear()
        countries.addAll(data)
        adapter.notifyDataSetChanged()
    }

}