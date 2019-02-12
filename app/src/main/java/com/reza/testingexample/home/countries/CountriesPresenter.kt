package com.reza.testingexample.home.countries

import com.google.gson.Gson
import com.reza.testingexample.data.api.ApiRepository
import com.reza.testingexample.data.api.CountriesApi
import com.reza.testingexample.data.model.Country
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.google.gson.reflect.TypeToken



class CountriesPresenter(
    private val view: CountriesView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {
    fun getCountriesByRegion(region: String) {
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main) {
            val listType = object : TypeToken<List<Country>>() { }.type
            val data = gson.fromJson<List<Country>>(apiRepository
                .doRequest(CountriesApi.getCountriesByRegion(region)).await(),
                listType
            )

            view.showCountryList(data)
            view.hideLoading()
        }
    }
}