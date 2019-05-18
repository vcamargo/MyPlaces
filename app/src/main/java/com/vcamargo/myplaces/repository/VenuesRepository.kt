package com.vcamargo.myplaces.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vcamargo.myplaces.model.VenueBasicDetails
import com.vcamargo.myplaces.model.VenueDetails
import com.vcamargo.myplaces.webservice.Webservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VenuesRepository(val webservice: Webservice) : IRepository {
    private var venuesLiveData : MutableLiveData<Resource<List<VenueBasicDetails>>>
    private var venueDetailsLiveData : MutableLiveData<Resource<VenueDetails>>

    companion object {
        const val ERROR_EMPTY_RESPONSE = "Empty Response"
    }

    init {
        venuesLiveData = MutableLiveData()
        venueDetailsLiveData = MutableLiveData()
    }

    override fun getVenues(ll: String): LiveData<Resource<List<VenueBasicDetails>>> {
        venuesLiveData.value = Resource.loading()
        webservice.getVenuesList(ll).enqueue(object : Callback<List<VenueBasicDetails>> {
            override fun onResponse(call: Call<List<VenueBasicDetails>>, response: Response<List<VenueBasicDetails>>) {
                val res = response.body()
                res?.let {
                    venuesLiveData.value = Resource.success(it)
                } ?: run {
                    venuesLiveData.value = Resource.error(ERROR_EMPTY_RESPONSE)
                }
            }

            override fun onFailure(call: Call<List<VenueBasicDetails>>, t: Throwable) {
                venuesLiveData.value = Resource.error(t.localizedMessage)
            }
        })

        return venuesLiveData
    }

    override fun getVenueDetails(venueId: String): LiveData<Resource<VenueDetails>> {
        venueDetailsLiveData.value = Resource.loading()

        webservice.getVenueDetails(venueId).enqueue(object : Callback<VenueDetails> {
            override fun onResponse(call: Call<VenueDetails>, response: Response<VenueDetails>) {
                val res = response.body()
                res?.let {
                    venueDetailsLiveData.value = Resource.success(it)
                } ?: run {
                    venueDetailsLiveData.value = Resource.error(ERROR_EMPTY_RESPONSE)
                }
            }

            override fun onFailure(call: Call<VenueDetails>, t: Throwable) {
                venueDetailsLiveData.value = Resource.error(t.localizedMessage)
            }
        })
        return venueDetailsLiveData
    }
}