package com.vcamargo.myplaces.model

import com.google.android.gms.maps.model.LatLng

data class VenueBasicDetails(
    val id: String,
    val name : String,
    val address : String,
    val latLng : LatLng?,
    val distance : String,
    val categoryName : String,
    val categoryImage : String
)