package com.vcamargo.myplaces.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vcamargo.myplaces.model.VenueBasicDetails
import com.vcamargo.myplaces.repository.IRepository
import com.vcamargo.myplaces.repository.Resource

class VenuesListViewModel(val repository: IRepository) : ViewModel() {
    var venuesLiveData : LiveData<Resource<List<VenueBasicDetails>>>? = null

    fun venuesSearch(latLng: String) {
        venuesLiveData = repository.getVenues(latLng)
    }
}