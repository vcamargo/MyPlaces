package com.vcamargo.myplaces.repository

import androidx.lifecycle.LiveData
import com.vcamargo.myplaces.model.VenueBasicDetails
import com.vcamargo.myplaces.model.VenueDetails

/**
 *
 * Interface with the methods to search for venues and retrieve details of a specific one
 *
 * **/
interface IRepository {
    /**
     *
     * Serach for venues in a specific latitude,longitude within a radius defined by Webservice.SEARCH_PARAM_RADIUS.
     * @see Webservice
     *
     * @param ll latitude and longitude of a given user location "-23.6815315,-46.8754902"
     * @return livedata with a list of venues
     *
     * **/
    fun getVenues(ll : String) : LiveData<Resource<List<VenueBasicDetails>>>

    /**
     *
     * Get details of a specific venue.
     *
     * @param venueId VenueId returned as part of getVenues() call. VenueBasicDetails.id
     * @see VenueBasicDetails
     * @return livedata with the details of that venue.
     * @see VenueDetails
     *
     * **/
    fun getVenueDetails(venueId : String) : LiveData<Resource<VenueDetails>>
}