package com.vcamargo.myplaces.converter

import android.util.Log
import com.vcamargo.myplaces.model.VenueBasicDetails
import com.google.android.gms.maps.model.LatLng
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import okhttp3.ResponseBody
import org.json.JSONException
import retrofit2.Converter
import java.io.IOException
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
        const val NO_VALUE = "-"
        const val LOG_TAG = "VenuesSearchConverter"
    }

    override fun convert(responseBody: ResponseBody): List<VenueBasicDetails> {
        var venuesList = mutableListOf<VenueBasicDetails>()

        try {
            var id : String? = null
            var name : String? = null
            var address : String? = null
            var distance : String? = null
            var latlng : LatLng? = null
            var categoryName : String? = null
            var categoryIconUrl : String? = null

            val res = responseBody.string()

            val jsonParser = JsonParser()
            val venues = jsonParser.parse(res).asJsonObject
                .getAsJsonObject(JSON_KEY_RESPONSE)
                .getAsJsonArray(JSON_KEY_VENUES)
            venues?.let {v->
                v.forEach {item->
                    val venue = item.asJsonObject
                    venue.takeIf { it != null }.apply {
                        id = this?.get(JSON_KEY_VENUE_ID)?.asString
                        name = this?.get(JSON_KEY_VENUE_NAME)?.asString

                        val location = this?.getAsJsonObject(JSON_KEY_VENUE_LOCATION)?.asJsonObject
                        location.takeIf { it != null }.apply {
                            address = this?.get(JSON_KEY_VENUE_LOCATION_ADDRESS)?.asString
                            val lat = this?.get(JSON_KEY_VENUE_LOCATION_LAT)?.asDouble
                            val lng = this?.get(JSON_KEY_VENUE_LOCATION_LNG)?.asDouble
                            distance = this?.get(JSON_KEY_VENUE_LOCATION_DISTANCE)?.asInt.toString()

                            lat?.let {
                                lng?.let {
                                    latlng = LatLng(lat, lng)
                                }
                            }
                        }

                        val category = this?.getAsJsonArray(JSON_KEY_VENUE_CATEGORIES)?.get(0)?.asJsonObject
                        category.takeIf { it != null }.apply {
                            categoryName = this?.get(JSON_KEY_VENUE_CATEGORY_NAME)?.asString
                            val icon = this?.getAsJsonObject(JSON_KEY_VENUE_CATEGORY_ICON)?.asJsonObject

                            icon.takeIf { it!= null }.apply {
                                val iconUrl = StringBuilder()
                                iconUrl.append(this?.get(JSON_KEY_VENUE_CATEGORY_ICON_PREFIX)?.asString)
                                iconUrl.append(JSON_KEY_VENUE_CATEGORY_ICON_SIZE)
                                iconUrl.append(this?.get(JSON_KEY_VENUE_CATEGORY_ICON_SUFFIX)?.asString)
                                categoryIconUrl = iconUrl.toString()
                            }
                        }

                        venuesList.add(
                            VenueBasicDetails(
                                id ?: NO_VALUE,
                                name ?: NO_VALUE,
                                address ?: NO_VALUE,
                                latlng,
                                distance ?: NO_VALUE,
                                categoryName ?: NO_VALUE,
                                categoryIconUrl ?: NO_VALUE
                            )
                        )
                    }
                }
            }

        } catch (e : JsonSyntaxException) {
            Log.e(LOG_TAG, e.localizedMessage)
        } catch (e: IOException) {
            Log.e(LOG_TAG, e.localizedMessage)
        } finally {
            return venuesList
        }
    }
}