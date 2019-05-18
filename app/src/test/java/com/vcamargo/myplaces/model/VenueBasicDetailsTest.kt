package com.vcamargo.myplaces.model

import com.google.android.gms.maps.model.LatLng
import org.junit.Test

class VenueBasicDetailsTest {
    val id = "5642aef9498e51025cf4a7a5"
    val name = "GoldBar - 골드바"
    val address = "용산구 이태원동 123-21"
    val latLng = LatLng(37.5348073,126.9925633)
    val distance = "8000 km"
    val categoryName  = "Bars"
    val categoryImage = "https://image.com/image.png"

    @Test
    fun testBuilder() {
        val venue = VenueBasicDetails(
            id,
            name,
            address,
            latLng,
            distance,
            categoryName,
            categoryImage
        )
    }
}