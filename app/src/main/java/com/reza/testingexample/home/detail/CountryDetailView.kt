package com.reza.testingexample.home.detail

import com.reza.testingexample.data.model.Country

interface CountryDetailView {
    fun showLoading()
    fun hideLoading()
    fun showCountryDetail(country: Country)
}