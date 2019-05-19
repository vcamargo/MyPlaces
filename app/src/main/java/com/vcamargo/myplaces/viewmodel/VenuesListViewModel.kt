package com.vcamargo.myplaces.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.vcamargo.myplaces.model.VenueBasicDetails
import com.vcamargo.myplaces.repository.IRepository
import com.vcamargo.myplaces.repository.Resource
import java.lang.StringBuilder

class VenuesListViewModel(val repository: IRepository) : ViewModel() {
    var venuesLiveData : LiveData<Resource<List<VenueBasicDetails>>>? = null
    var lastKnownLocation = MutableLiveData<LatLng>()

    fun venuesSearch() {
        val latlng = StringBuilder()
        latlng.append(lastKnownLocation?.value?.latitude)
        latlng.append(",")
        latlng.append(lastKnownLocation?.value?.longitude)

        venuesLiveData = repository.getVenues(latlng.toString())
    }
}