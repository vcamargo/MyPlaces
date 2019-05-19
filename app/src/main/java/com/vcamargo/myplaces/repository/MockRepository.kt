package com.vcamargo.myplaces.repository

import android.content.Context
import androidx.lifecycle.LiveData
import android.os.Handler
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.vcamargo.myplaces.converter.VenueDetailsConverter
import com.vcamargo.myplaces.converter.VenuesSearchConverter
import com.vcamargo.myplaces.model.VenueBasicDetails
import com.vcamargo.myplaces.model.VenueDetails
import okhttp3.MediaType
import okhttp3.ResponseBody

class MockRepository(
    val handler : Handler,
    val mContext : Context
) : IRepository {

    companion object {
        const val DELAY = 2000
        const val VENUES_SEARCH_JSON_FILENAME = "search.json"
        const val VENUE_DETAILS_JSON_FILENAME = "venues.json"
    }

    private var venuesLiveData : MutableLiveData<Resource<List<VenueBasicDetails>>>
    private var venueDetailsLiveData : MutableLiveData<Resource<VenueDetails>>
    private val mediaType = MediaType.get("application/json")

    init {
        venuesLiveData = MutableLiveData()
        venueDetailsLiveData = MutableLiveData()
    }


    override fun getVenueDetails(venueId: String): LiveData<Resource<VenueDetails>> {
        venueDetailsLiveData.value = Resource.loading()

        handler.postDelayed({
            mContext.assets.open(VENUE_DETAILS_JSON_FILENAME).use { inputStream ->
                val responseBody = ResponseBody.create(mediaType, inputStream.readBytes())
                val response = VenueDetailsConverter().convert(responseBody)
                response?.let {
                    venueDetailsLiveData.value = Resource.success(response)
                }
            }
        }, 2000)

        return venueDetailsLiveData
    }

    override fun getVenues(ll: String): LiveData<Resource<List<VenueBasicDetails>>> {
        venuesLiveData.value = Resource.loading()

        handler.postDelayed({
            mContext.assets.open(VENUES_SEARCH_JSON_FILENAME).use { inputStream ->
                val responseBody = ResponseBody.create(mediaType, inputStream.readBytes())
                val response = VenuesSearchConverter().convert(responseBody)
                venuesLiveData.value = Resource.success(response)
            }
        }, 2000)

        return venuesLiveData
    }
}