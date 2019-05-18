package com.vcamargo.myplaces.webservice

import com.vcamargo.myplaces.model.VenueBasicDetails
import com.vcamargo.myplaces.model.VenueDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Webservice {
    companion object {
        const val BASE_URL = "https://api.foursquare.com/v2/"
    }

    /**
     * @GET HTTP GET to retrieve a list of venues near the current location
     *
     * https://developer.foursquare.com/docs/api/venues/search
     * https://developer.foursquare.com/docs/resources/categories
     *
     * &intent=browse
     * &radius=500
     * &categoryId=4d4b7105d754a06374d81259
     * &v=20180515
     * &client_id=NHRNPTONQG4VDOWYDLWP34SLQM4DVXQIFHC0KY4UWMU3ACYR
     * &client_secret=WI3ZDFGFKVTKSDJHQSF0HD4TLJRLITYTJAZZMYCLWFVKTFEQ
     * **/
    @GET("venues/search?intent=browse&radius=500&categoryId=4d4b7105d754a06374d81259" +
            "&client_id=NHRNPTONQG4VDOWYDLWP34SLQM4DVXQIFHC0KY4UWMU3ACYR" +
            "&client_secret=WI3ZDFGFKVTKSDJHQSF0HD4TLJRLITYTJAZZMYCLWFVKTFEQ" +
            "&v=20180515")
    fun getVenuesList(@Query("ll") latlang: String) : Call<List<VenueBasicDetails>>

    /**
     * @GET HTTP GET to retrieve a venue details
     *
     * https://developer.foursquare.com/docs/api/venues/details
     *
     * **/
    @GET("venues/{id}" +
            "?client_id=NHRNPTONQG4VDOWYDLWP34SLQM4DVXQIFHC0KY4UWMU3ACYR" +
            "&client_secret=WI3ZDFGFKVTKSDJHQSF0HD4TLJRLITYTJAZZMYCLWFVKTFEQ" +
            "&v=20180515")
    fun getVenueDetails(@Path("id") venueId: String) : Call<VenueDetails>
}