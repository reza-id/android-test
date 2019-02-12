package com.reza.testingexample.home.countries

import com.reza.testingexample.data.model.Country

interface CountriesView {
    fun showLoading()
    fun hideLoading()
    fun showCountryList(data: List<Country>)
}