package com.reza.testingexample.data.db

data class FavoriteCountry(val id: Long?, val alpha2Code: String?, val name: String?, val capital: String?) {
    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val COUNTRY_CODE: String = "COUNTRY_CODE"
        const val COUNTRY_NAME: String = "COUNTRY_NAME"
        const val CAPITAL_CITY: String = "CAPITAL_CITY"
    }
}