package com.vcamargo.myplaces.model

import org.junit.Assert
import org.junit.Test

class VenueDetailsTest {
    val name = "GoldBar - 골드바"
    val formattedPhone = "(31) 99999999"
    val formattedAddress = "용산구 이태원동 123-21\n" + "Seoul, Korea 140-200"
    val hours = "Open"
    val categories = "Bar"
    val price = "Cheap $"
    val rating = 10
    val photoUrl = "https://image.com/image.png"

    @Test
    fun testBuilder() {
        val venueDetails = VenueDetails(
            name,
            formattedPhone,
            formattedAddress,
            hours,
            categories,
            price,
            rating,
            photoUrl)

        Assert.assertNotNull(venueDetails)
        Assert.assertEquals(name, venueDetails.name)
        Assert.assertEquals(formattedPhone, venueDetails.formattedPhone)
        Assert.assertEquals(formattedAddress, venueDetails.formattedAddress)
        Assert.assertEquals(hours, venueDetails.hours)
        Assert.assertEquals(categories, venueDetails.categories)
        Assert.assertEquals(price, venueDetails.price)
        Assert.assertEquals(rating, venueDetails.rating)
        Assert.assertEquals(photoUrl, venueDetails.photoUrl)
    }
}