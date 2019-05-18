package com.vcamargo.myplaces.repository

import androidx.lifecycle.LiveData
import android.os.Handler
import com.vcamargo.myplaces.model.VenueBasicDetails
import com.vcamargo.myplaces.model.VenueDetails

class MockRepository(val handler: Handler) : IRepository {
    override fun getVenueDetails(venueId: String): LiveData<Resource<VenueDetails>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getVenues(ll: String): LiveData<Resource<List<VenueBasicDetails>>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}