package com.reza.testingexample.data.api

import com.reza.testingexample.BuildConfig

object CountriesApi {

    fun getCountriesByRegion(regionCode: String): String {
        return BuildConfig.BASE_URL + "region/$regionCode"
    }

    fun getCountryByCode(code: String): String {
        return BuildConfig.BASE_URL + "alpha/$code"
    }

}
