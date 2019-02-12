package com.reza.testingexample.home.detail

import com.google.gson.Gson
import com.reza.testingexample.data.api.ApiRepository
import com.reza.testingexample.data.api.CountriesApi
import com.reza.testingexample.data.model.Country
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CountryDetailPresenter(private val view: CountryDetailView,
                             private val apiRepository: ApiRepository,
                             private val gson: Gson) {

    fun getCountryDetail(code: String) {
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository
                    .doRequest(CountriesApi.getCountryByCode(code)).await(),
                    Country::class.java
            )
            view.showCountryDetail(data)
            view.hideLoading()
        }

    }
}
