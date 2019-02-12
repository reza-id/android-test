package com.reza.testingexample.data.api

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class ApiRepositoryTest {

    @Test
    fun testDoRequest() {
        val apiRepository = mock(ApiRepository::class.java)
        val url = "https://restcountries.eu/rest/v2/region/asia"
        apiRepository.doRequest(url)
        verify(apiRepository).doRequest(url)
    }
}
