package com.vcamargo.myplaces.converter

import com.google.android.gms.maps.model.LatLng
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Test

class VenuesSearchConverterTest {
    companion object {
        const val API_RESPONSE = "{\n" +
                "  \"meta\": {\n" +
                "    \"code\": 200,\n" +
                "    \"requestId\": \"5ac51d7e6a607143d811cecb\"\n" +
                "  },\n" +
                "  \"response\": {\n" +
                "    \"venues\": [\n" +
                "      {\n" +
                "        \"id\": \"5642aef9498e51025cf4a7a5\",\n" +
                "        \"name\": \"Mr. Purple\",\n" +
                "        \"location\": {\n" +
                "          \"address\": \"180 Orchard St\",\n" +
                "          \"crossStreet\": \"btwn Houston & Stanton St\",\n" +
                "          \"lat\": 40.72173744277209,\n" +
                "          \"lng\": -73.98800687282996,\n" +
                "          \"labeledLatLngs\": [\n" +
                "            {\n" +
                "              \"label\": \"display\",\n" +
                "              \"lat\": 40.72173744277209,\n" +
                "              \"lng\": -73.98800687282996\n" +
                "            }\n" +
                "          ],\n" +
                "          \"distance\": 8,\n" +
                "          \"postalCode\": \"10002\",\n" +
                "          \"cc\": \"US\",\n" +
                "          \"city\": \"New York\",\n" +
                "          \"state\": \"NY\",\n" +
                "          \"country\": \"United States\",\n" +
                "          \"formattedAddress\": [\n" +
                "            \"180 Orchard St (btwn Houston & Stanton St)\",\n" +
                "            \"New York, NY 10002\",\n" +
                "            \"United States\"\n" +
                "          ]\n" +
                "        },\n" +
                "        \"categories\": [\n" +
                "          {\n" +
                "            \"id\": \"4bf58dd8d48988d1d5941735\",\n" +
                "            \"name\": \"Hotel Bar\",\n" +
                "            \"pluralName\": \"Hotel Bars\",\n" +
                "            \"shortName\": \"Hotel Bar\",\n" +
                "            \"icon\": {\n" +
                "              \"prefix\": \"https://ss3.4sqi.net/img/categories_v2/travel/hotel_bar_\",\n" +
                "              \"suffix\": \".png\"\n" +
                "            },\n" +
                "            \"primary\": true\n" +
                "          }\n" +
                "        ],\n" +
                "        \"venuePage\": {\n" +
                "          \"id\": \"150747252\"\n" +
                "        }\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}"
    }


    private val mediaType = MediaType.get("application/json")

    @Test
    fun convertTest() {
        val responseBody = ResponseBody.create(mediaType, API_RESPONSE)
        val response = VenuesSearchConverter().convert(responseBody)

        Assert.assertNotNull(response)
        Assert.assertEquals(1, response.size)
        val venue = response[0]
        Assert.assertEquals("5642aef9498e51025cf4a7a5", venue.id)
        Assert.assertEquals("Mr. Purple", venue.name)
        Assert.assertEquals("180 Orchard St", venue.address)
        Assert.assertEquals(LatLng(40.72173744277209,-73.98800687282996), venue.latLng)
        Assert.assertEquals("8", venue.distance)
        Assert.assertEquals("Hotel Bar", venue.categoryName)
        Assert.assertEquals("https://ss3.4sqi.net/img/categories_v2/travel/hotel_bar_bg_44.png",
            venue.categoryImage)
    }

    @Test
    fun convertWrongResponse() {
        val responseBody = ResponseBody.create(mediaType, VenueDetailsConverterTest.API_RESPONSE)
        val response = VenuesSearchConverter().convert(responseBody)

        Assert.assertNotNull(response)
        Assert.assertEquals(0, response.size)
    }
}