package com.reza.testingexample.home.countries

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.reza.testingexample.TestContextProvider
import com.reza.testingexample.data.api.ApiRepository
import com.reza.testingexample.data.model.Country
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CountriesPresenterTest {

    @Mock
    private lateinit var view: CountriesView
    @Mock
    private lateinit var gson: Gson
    @Mock
    private lateinit var apiRepository: ApiRepository
    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenter: CountriesPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = CountriesPresenter(view, apiRepository, gson, TestContextProvider())
    }

    var region = ""

    @Test
    fun getCountriesByRegion() {
        val countries = mutableListOf<Country>()

        givenARegion()

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await())
                .thenReturn("")

            Mockito.`when`(
                gson.fromJson<List<Country>>(
                    "",
                    object : TypeToken<List<Country>>() {}.type
                )
            )
                .thenReturn(countries)

            presenter.getCountriesByRegion(region)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showCountryList(countries)
            Mockito.verify(view).hideLoading()
        }
    }

    private fun givenARegion() {
        region = "Asia"
    }
}