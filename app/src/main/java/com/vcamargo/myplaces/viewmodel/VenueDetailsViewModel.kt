package com.vcamargo.myplaces.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vcamargo.myplaces.model.VenueDetails
import com.vcamargo.myplaces.repository.IRepository
import com.vcamargo.myplaces.repository.Resource

class VenueDetailsViewModel (val repository: IRepository) : ViewModel() {
    var venueDetailsData : LiveData<Resource<VenueDetails>>? = null

    fun getVenueDetails(venueId: String) {
        venueDetailsData = repository.getVenueDetails(venueId)
    }
}