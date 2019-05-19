package com.vcamargo.myplaces.converter

import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Test

class VenueDetailsConverterTest {
    companion object {
        const val EXPECTED = "-"
        const val API_RESPONSE = "{\n" +
                "  \"meta\": {\n" +
                "    \"code\": 200,\n" +
                "    \"requestId\": \"59a45921351e3d43b07028b5\"\n" +
                "  },\n" +
                "  \"response\": {\n" +
                "    \"venue\": {\n" +
                "      \"id\": \"412d2800f964a520df0c1fe3\",\n" +
                "      \"name\": \"Central Park\",\n" +
                "      \"contact\": {\n" +
                "        \"phone\": \"2123106600\",\n" +
                "        \"formattedPhone\": \"(212) 310-6600\",\n" +
                "        \"twitter\": \"centralparknyc\",\n" +
                "        \"instagram\": \"centralparknyc\",\n" +
                "        \"facebook\": \"37965424481\",\n" +
                "        \"facebookUsername\": \"centralparknyc\",\n" +
                "        \"facebookName\": \"Central Park\"\n" +
                "      },\n" +
                "      \"location\": {\n" +
                "        \"address\": \"59th St to 110th St\",\n" +
                "        \"crossStreet\": \"5th Ave to Central Park West\",\n" +
                "        \"lat\": 40.78408342593807,\n" +
                "        \"lng\": -73.96485328674316,\n" +
                "        \"postalCode\": \"10028\",\n" +
                "        \"cc\": \"US\",\n" +
                "        \"city\": \"New York\",\n" +
                "        \"state\": \"NY\",\n" +
                "        \"country\": \"United States\",\n" +
                "        \"formattedAddress\": [\n" +
                "          \"59th St to 110th St (5th Ave to Central Park West)\",\n" +
                "          \"New York, NY 10028\",\n" +
                "          \"United States\"\n" +
                "        ]\n" +
                "      },\n" +
                "      \"canonicalUrl\": \"https://foursquare.com/v/central-park/412d2800f964a520df0c1fe3\",\n" +
                "      \"categories\": [\n" +
                "        {\n" +
                "          \"id\": \"4bf58dd8d48988d163941735\",\n" +
                "          \"name\": \"Park\",\n" +
                "          \"pluralName\": \"Parks\",\n" +
                "          \"shortName\": \"Park\",\n" +
                "          \"icon\": {\n" +
                "            \"prefix\": \"https://ss3.4sqi.net/img/categories_v2/parks_outdoors/park_\",\n" +
                "            \"suffix\": \".png\"\n" +
                "          },\n" +
                "          \"primary\": true\n" +
                "        }\n" +
                "      ],\n" +
                "      \"verified\": true,\n" +
                "      \"url\": \"http://www.centralparknyc.org\",\n" +
                "      \"rating\": 9.8,\n" +
                "      \"ratingColor\": \"00B551\",\n" +
                "      \"ratingSignals\": 18854,\n" +
                "      \"photos\": {\n" +
                "        \"count\": 26681,\n" +
                "        \"groups\": [\n" +
                "          {\n" +
                "            \"type\": \"venue\",\n" +
                "            \"name\": \"Venue photos\",\n" +
                "            \"count\": 26681,\n" +
                "            \"items\": [\n" +
                "              {\n" +
                "                \"id\": \"513bd223e4b0e8ef8292ee54\",\n" +
                "                \"createdAt\": 1362874915,\n" +
                "                \"source\": {\n" +
                "                  \"name\": \"Instagram\",\n" +
                "                  \"url\": \"http://instagram.com\"\n" +
                "                },\n" +
                "                \"prefix\": \"https://igx.4sqi.net/img/general/\",\n" +
                "                \"suffix\": \"/655018_Zp3vA90Sy4IIDApvfAo5KnDItoV0uEDZeST7bWT-qzk.jpg\",\n" +
                "                \"width\": 612,\n" +
                "                \"height\": 612,\n" +
                "                \"user\": {\n" +
                "                  \"id\": \"123456\",\n" +
                "                  \"firstName\": \"John\",\n" +
                "                  \"lastName\": \"Doe\",\n" +
                "                  \"gender\": \"male\"\n" +
                "                },\n" +
                "                \"visibility\": \"public\"\n" +
                "              }\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      \"description\": \"Central Park is the 843-acre green heart of Manhattan and is maintained by the Central Park Conservancy. It was designed in the 19th century by Frederick Law Olmsted and Calvert Vaux as an urban escape for New Yorkers, and now receives over 40 million visits per year.\",\n" +
                "      \"storeId\": \"\",\n" +
                "      \"createdAt\": 1093478400,\n" +
                "      \"shortUrl\": \"http://4sq.com/2UsPUp\",\n" +
                "      \"timeZone\": \"America/New_York\",\n" +
                "      \"hours\": {\n" +
                "        \"status\": \"Open until 1:00 AM\",\n" +
                "        \"isOpen\": true,\n" +
                "        \"isLocalHoliday\": false,\n" +
                "        \"timeframes\": [\n" +
                "          {\n" +
                "            \"days\": \"Mon–Sun\",\n" +
                "            \"includesToday\": true,\n" +
                "            \"open\": [\n" +
                "              {\n" +
                "                \"renderedTime\": \"6:00 AM–1:00 AM\"\n" +
                "              }\n" +
                "            ],\n" +
                "            \"segments\": []\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      \"bestPhoto\": {\n" +
                "        \"id\": \"513bd223e4b0e8ef8292ee54\",\n" +
                "        \"createdAt\": 1362874915,\n" +
                "        \"source\": {\n" +
                "          \"name\": \"Instagram\",\n" +
                "          \"url\": \"http://instagram.com\"\n" +
                "        },\n" +
                "        \"prefix\": \"https://igx.4sqi.net/img/general/\",\n" +
                "        \"suffix\": \"/655018_Zp3vA90Sy4IIDApvfAo5KnDItoV0uEDZeST7bWT-qzk.jpg\",\n" +
                "        \"width\": 612,\n" +
                "        \"height\": 612,\n" +
                "        \"visibility\": \"public\"\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}"
    }

    private val mediaType = MediaType.get("application/json")


    @Test
    fun convertTest() {
        val responseBody = ResponseBody.create(mediaType, API_RESPONSE)
        val response = VenueDetailsConverter().convert(responseBody)

        Assert.assertNotNull(response)
        Assert.assertEquals("Central Park", response?.name)
        Assert.assertEquals("(212) 310-6600", response?.formattedPhone)
        Assert.assertEquals("59th St to 110th St (5th Ave to Central Park West)\n" +
                "New York, NY 10028\n" +
                "United States\n", response?.formattedAddress)
        Assert.assertEquals("Open until 1:00 AM", response?.hours)
        Assert.assertEquals("Park", response?.categories?.trim())
        Assert.assertEquals("-", response?.price)
        Assert.assertEquals(9, response?.rating)
        Assert.assertEquals("https://igx.4sqi.net/img/general/500x500/655018_" +
                "Zp3vA90Sy4IIDApvfAo5KnDItoV0uEDZeST7bWT-qzk.jpg", response?.photoUrl)
    }

    @Test
    fun convertWrongResponse() {
        val responseBody = ResponseBody.create(mediaType, VenuesSearchConverterTest.API_RESPONSE)
        val response = VenueDetailsConverter().convert(responseBody)

        Assert.assertNotNull(response)
        Assert.assertEquals(EXPECTED, response?.name)
        Assert.assertEquals(EXPECTED, response?.formattedPhone)
        Assert.assertEquals(EXPECTED, response?.formattedAddress)
        Assert.assertEquals(EXPECTED, response?.hours)
        Assert.assertEquals(EXPECTED, response?.categories?.trim())
        Assert.assertEquals(EXPECTED, response?.price)
        Assert.assertEquals(0, response?.rating)
        Assert.assertEquals(EXPECTED, response?.photoUrl)
    }
}