package com.vcamargo.myplaces.model

data class VenueDetails(
    val name : String,
    val formattedPhone : String,
    val formattedAddress : String,
    val hours : String,
    val categories : String,
    val price : String,
    val rating : String,
    val photoUrl : String
)