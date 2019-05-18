package com.vcamargo.myplaces.converter

import android.util.Log
import com.vcamargo.myplaces.converter.VenuesSearchConverter.Companion.JSON_KEY_VENUE_CATEGORY_ICON_PREFIX
import com.vcamargo.myplaces.converter.VenuesSearchConverter.Companion.JSON_KEY_VENUE_CATEGORY_ICON_SUFFIX
import com.vcamargo.myplaces.converter.VenuesSearchConverter.Companion.JSON_KEY_VENUE_NAME
import com.vcamargo.myplaces.model.VenueDetails
import com.google.gson.JsonParser
import okhttp3.ResponseBody
import org.json.JSONException
import retrofit2.Converter
import java.lang.StringBuilder

class VenueDetailsConverter : Converter<ResponseBody, VenueDetails> {
    companion object {
        const val JSON_KEY_VENUE = "venue"
        const val JSON_KEY_VENUE_CONTACT = "contact"
        const val JSON_KEY_HOURS = "hours"
        const val JSON_KEY_PRICE = "price"
        const val JSON_KEY_RATING = "rating"
        const val JSON_KEY_PHOTOS = "bestPhoto"
        const val JSON_KEY_STATUS = "status"
        const val JSON_KEY_FORMATTED_ADDRESS = "formattedAddress"
        const val PHOTO_SIZE = "500x500"
        const val LOG_TAG = "VenueDetailsConverter"
    }
    override fun convert(responseBody: ResponseBody): VenueDetails? {
        var venueDetails : VenueDetails? = null
        try {
            var status : String? = null
            val res = responseBody.string()

            val jsonParser = JsonParser()
            val response = jsonParser.parse(res).asJsonObject
                .getAsJsonObject(VenuesSearchConverter.JSON_KEY_RESPONSE)

            val venue = response.asJsonObject
                .getAsJsonObject(JSON_KEY_VENUE)
            val name = venue.asJsonObject.get(JSON_KEY_VENUE_NAME).asString

            val contact = venue.asJsonObject
                .getAsJsonObject(JSON_KEY_VENUE_CONTACT)

            val location = venue.asJsonObject
                .getAsJsonObject(VenuesSearchConverter.JSON_KEY_VENUE_LOCATION)
            val formatedAddressArr = location.asJsonObject
                .getAsJsonArray(JSON_KEY_FORMATTED_ADDRESS)
            val formatedAddress = StringBuilder()
            formatedAddressArr.forEach {
                formatedAddress.append("\n")
                formatedAddress.append(it)
            }

            val hours = venue.asJsonObject
                .getAsJsonObject(JSON_KEY_HOURS)
            hours.takeIf { it != null }.apply {
                status = this?.asJsonObject?.get(JSON_KEY_STATUS)?.asString
            }

            val categoriesArr = venue.asJsonObject
                .getAsJsonArray(VenuesSearchConverter.JSON_KEY_VENUE_CATEGORIES)
            val categories = StringBuilder()
            categoriesArr.forEach {
                categories.append(it.asJsonObject.get(JSON_KEY_VENUE_NAME).asString + " ")
            }
            val price = venue.asJsonObject
                .getAsJsonObject(JSON_KEY_PRICE)

            var rating : String? = null
            val ratingJson = venue.asJsonObject.get(JSON_KEY_RATING)
            ratingJson.takeIf { it != null }.apply {
                rating = this?.asInt?.toString()
            }

            val photo = venue.asJsonObject
                .getAsJsonObject(JSON_KEY_PHOTOS)
            val photoUrl = StringBuilder()
            photoUrl.append(photo.asJsonObject.get(JSON_KEY_VENUE_CATEGORY_ICON_PREFIX).asString)
            photoUrl.append(PHOTO_SIZE)
            photoUrl.append(photo.asJsonObject.get(JSON_KEY_VENUE_CATEGORY_ICON_SUFFIX).asString)

            venueDetails = VenueDetails(
                name,
                "",
                formatedAddress.toString(),
                status ?: "",
                categories.toString(),
                "",
                rating ?: "",
                photoUrl.toString()
            )

        } catch (e: JSONException) {
            Log.e(LOG_TAG, e.localizedMessage)
        } finally {
            return venueDetails
        }
    }
}