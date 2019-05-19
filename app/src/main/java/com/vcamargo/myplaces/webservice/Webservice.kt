package com.vcamargo.myplaces.webservice

import com.vcamargo.myplaces.model.VenueBasicDetails
import com.vcamargo.myplaces.model.VenueDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *
 * Interface with the definitions of the HTTP calls to the Foursquare API
 *
 * **/
interface Webservice {

    companion object {
        //Webservice base url
        const val BASE_URL = "https://api.foursquare.com/v2/"
        // foursquare api Userless Auth
        const val FOURSQUARE_API_CLIENT_ID = ""
        // foursquare api Userless Auth
        const val FOURSQUARE_API_CLIENT_SECRET = ""
        // foursquare api Versioning
        const val FOURSQUARE_API_DATE = "20180518"
        // venues search param category id
        const val SEARCH_PARAM_CATEGORY_ID = "4d4b7105d754a06374d81259"
        // venues search param raduis
        const val SEARCH_PARAM_RADIUS = "500"
        // foursquare api Userless Auth client_id
        const val FOURSQUARE_API_AUTH_CLIENT_ID = "client_id"
        // foursquare api Userless Auth client_secret
        const val FOURSQUARE_API_AUTH_CLIENT_SECRET = "client_secret"
    }

    /**
     * @GET HTTP GET to retrieve a list of venues near the current location
     *
     * https://developer.foursquare.com/docs/api/venues/search
     * https://developer.foursquare.com/docs/resources/categories
     *
     * **/
    @GET("venues/search?intent=browse" +
            "&radius=$SEARCH_PARAM_RADIUS" +
            "&categoryId=$SEARCH_PARAM_CATEGORY_ID" +
            "&$FOURSQUARE_API_AUTH_CLIENT_ID=$FOURSQUARE_API_CLIENT_ID" +
            "&$FOURSQUARE_API_AUTH_CLIENT_SECRET=$FOURSQUARE_API_CLIENT_SECRET" +
            "&v=$FOURSQUARE_API_DATE")
    fun getVenuesList(@Query("ll") latlang: String) : Call<List<VenueBasicDetails>>

    /**
     * @GET HTTP GET to retrieve a venue details
     *
     * https://developer.foursquare.com/docs/api/venues/details
     *
     * **/
    @GET("venues/{id}?$FOURSQUARE_API_AUTH_CLIENT_ID=$FOURSQUARE_API_CLIENT_ID" +
            "&$FOURSQUARE_API_AUTH_CLIENT_SECRET=$FOURSQUARE_API_CLIENT_SECRET" +
            "&v=$FOURSQUARE_API_DATE")
    fun getVenueDetails(@Path("id") venueId: String) : Call<VenueDetails>
}