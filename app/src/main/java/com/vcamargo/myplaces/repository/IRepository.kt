package com.vcamargo.myplaces.repository

import androidx.lifecycle.LiveData
import com.vcamargo.myplaces.model.VenueBasicDetails
import com.vcamargo.myplaces.model.VenueDetails

interface IRepository {
    fun getVenues(ll : String) : LiveData<Resource<List<VenueBasicDetails>>>
    fun getVenueDetails(venueId : String) : LiveData<Resource<VenueDetails>>
}