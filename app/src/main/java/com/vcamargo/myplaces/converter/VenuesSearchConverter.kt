package com.vcamargo.myplaces.converter

import android.util.Log
import com.vcamargo.myplaces.model.VenueBasicDetails
import com.google.android.gms.maps.model.LatLng
import com.google.gson.JsonParser
import okhttp3.ResponseBody
import org.json.JSONException
import retrofit2.Converter
import java.lang.StringBuilder

class VenuesSearchConverter : Converter<ResponseBody, List<VenueBasicDetails>> {
    companion object {
        const val JSON_KEY_RESPONSE = "response"
        const val JSON_KEY_VENUES = "venues"
        const val JSON_KEY_VENUE_ID = "id"
        const val JSON_KEY_VENUE_NAME = "name"
        const val JSON_KEY_VENUE_LOCATION = "location"
        const val JSON_KEY_VENUE_LOCATION_ADDRESS = "address"
        const val JSON_KEY_VENUE_LOCATION_LAT = "lat"
        const val JSON_KEY_VENUE_LOCATION_LNG = "lng"
        const val JSON_KEY_VENUE_LOCATION_DISTANCE = "distance"
        const val JSON_KEY_VENUE_CATEGORIES = "categories"
        const val JSON_KEY_VENUE_CATEGORY_NAME = "name"
        const val JSON_KEY_VENUE_CATEGORY_ICON = "icon"
        const val JSON_KEY_VENUE_CATEGORY_ICON_PREFIX = "prefix"
        const val JSON_KEY_VENUE_CATEGORY_ICON_SIZE = "bg_44"
        const val JSON_KEY_VENUE_CATEGORY_ICON_SUFFIX = "suffix"
        const val LOG_TAG = "VenuesSearchConverter"
    }

    override fun convert(responseBody: ResponseBody): List<VenueBasicDetails> {
        var venuesList = mutableListOf<VenueBasicDetails>()

        try {
            val res = responseBody.string()

            val jsonParser = JsonParser()
            val venues = jsonParser.parse(res).asJsonObject
                .getAsJsonObject(JSON_KEY_RESPONSE)
                .getAsJsonArray(JSON_KEY_VENUES)
            venues.forEach {
                val venue = it.asJsonObject
                val id = venue.get(JSON_KEY_VENUE_ID).asString
                val name = venue.get(JSON_KEY_VENUE_NAME).asString

                val location = venue.getAsJsonObject(JSON_KEY_VENUE_LOCATION).asJsonObject
                val address = location.get(JSON_KEY_VENUE_LOCATION_ADDRESS).asString
                val lat = location.get(JSON_KEY_VENUE_LOCATION_LAT).asDouble
                val lng = location.get(JSON_KEY_VENUE_LOCATION_LNG).asDouble
                val latlng = LatLng(lat, lng)
                val distance = location.get(JSON_KEY_VENUE_LOCATION_DISTANCE).asInt.toString()

                val category = venue.getAsJsonArray(JSON_KEY_VENUE_CATEGORIES)[0].asJsonObject
                val categoryName = category.get(JSON_KEY_VENUE_CATEGORY_NAME).asString
                val icon = category.getAsJsonObject(JSON_KEY_VENUE_CATEGORY_ICON).asJsonObject

                val iconUrl = StringBuilder()
                iconUrl.append(icon.get(JSON_KEY_VENUE_CATEGORY_ICON_PREFIX).asString)
                iconUrl.append(JSON_KEY_VENUE_CATEGORY_ICON_SIZE)
                iconUrl.append(icon.get(JSON_KEY_VENUE_CATEGORY_ICON_SUFFIX).asString)
                val categoryIconUrl = iconUrl.toString()

                venuesList.add(
                    VenueBasicDetails(
                        id,
                        name,
                        address,
                        latlng,
                        distance,
                        categoryName,
                        categoryIconUrl
                    )
                )
            }

        } catch (e: JSONException) {
            Log.e(LOG_TAG, e.localizedMessage)
        } finally {
            return venuesList
        }
    }
}