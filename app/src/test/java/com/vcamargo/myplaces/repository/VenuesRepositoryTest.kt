package com.vcamargo.myplaces.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vcamargo.myplaces.converter.VenueDetailsConverterTest
import com.vcamargo.myplaces.converter.VenuesConverterFactory
import com.vcamargo.myplaces.converter.VenuesSearchConverterTest
import com.vcamargo.myplaces.webservice.Webservice
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import retrofit2.Retrofit

class VenuesRepositoryTest {

    var retrofit: Retrofit? = null
    var webservice : Webservice? = null
    var server : MockWebServer? = null
    var repository : VenuesRepository? = null

    // enforce livedata to execute synchronously, instead of async
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUpServer() {
        server =  MockWebServer()
        server?.enqueue(MockResponse().setBody(VenuesSearchConverterTest.API_RESPONSE))
        server?.enqueue(MockResponse().setBody(VenueDetailsConverterTest.API_RESPONSE))
        server?.start()

        val baseUrl = server?.url("/mockserver/")

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl?.toString())
            .addConverterFactory(VenuesConverterFactory())
            .build()

        webservice = retrofit?.create(Webservice::class.java)
        webservice?.let {
            repository = VenuesRepository(it)
        }
    }

    @Test
    fun testRepositoryResponse() {
        Assert.assertNotNull(repository)
        repository?.let {repo->
            val response = repo.getVenues("")
            Assert.assertEquals(Resource.Status.LOADING, response.value?.status)
            var venues = response?.value?.data
            Assert.assertNull(venues)

            //this looks lame but it's an easy way to ensure that the repository will first
            // send Loading status, then either Success or Error
            Thread.sleep(2000)
            Assert.assertEquals(Resource.Status.SUCCESS, response.value?.status)
            venues = response?.value?.data
            Assert.assertEquals(1, venues?.size)

            val nextResponse = repo.getVenueDetails("")
            Assert.assertEquals(Resource.Status.LOADING, nextResponse.value?.status)
            var venueDetails = nextResponse?.value?.data
            Assert.assertNull(venueDetails)

            Thread.sleep(2000)
            Assert.assertEquals(Resource.Status.SUCCESS, nextResponse.value?.status)
            venueDetails = nextResponse?.value?.data
            Assert.assertEquals("Central Park", venueDetails?.name)
        }
    }

    @After
    fun tearDown() {
        server?.shutdown()
    }

}