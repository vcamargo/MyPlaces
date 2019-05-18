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
import java.lang.NumberFormatException
import java.lang.StringBuilder

class VenueDetailsConverter : Converter<ResponseBody, VenueDetails> {
    companion object {
        const val JSON_KEY_VENUE = "venue"
        const val JSON_KEY_VENUE_CONTACT = "contact"
        const val JSON_KEY_VENUE_CONTACT_PHONE = "formattedPhone"
        const val JSON_KEY_HOURS = "hours"
        const val JSON_KEY_PRICE = "price"
        const val JSON_KEY_RATING = "rating"
        const val JSON_KEY_PHOTOS = "bestPhoto"
        const val JSON_KEY_STATUS = "status"
        const val JSON_KEY_PRICE_TIER = "tier"
        const val JSON_KEY_PRICE_MESSAGE = "message"
        const val JSON_KEY_PRICE_CURRENCY = "currency"
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

            // Name
            val name = venue.asJsonObject.get(JSON_KEY_VENUE_NAME).asString

            // Phone
            val contact = venue.asJsonObject
                .getAsJsonObject(JSON_KEY_VENUE_CONTACT)
            val phone = contact.asJsonObject
                .get(JSON_KEY_VENUE_CONTACT_PHONE).asString

            // Address
            val location = venue.asJsonObject
                .getAsJsonObject(VenuesSearchConverter.JSON_KEY_VENUE_LOCATION)
            val formatedAddressArr = location.asJsonObject
                .getAsJsonArray(JSON_KEY_FORMATTED_ADDRESS)
            val formatedAddress = StringBuilder()
            formatedAddressArr.forEach {
                formatedAddress.append(it.asString)
                formatedAddress.append("\n")
            }

            // Hours
            val hours = venue.asJsonObject
                .getAsJsonObject(JSON_KEY_HOURS)
            hours.takeIf { it != null }.apply {
                status = this?.asJsonObject?.get(JSON_KEY_STATUS)?.asString
            }

            // Categories
            val categoriesArr = venue.asJsonObject
                .getAsJsonArray(VenuesSearchConverter.JSON_KEY_VENUE_CATEGORIES)
            val categories = StringBuilder()
            categoriesArr.forEach {
                categories.append(it.asJsonObject.get(JSON_KEY_VENUE_NAME).asString + " ")
            }

            // Price
            val price = venue.asJsonObject
                .getAsJsonObject(JSON_KEY_PRICE)
            var priceFormatted = StringBuilder()
            price?.let {p ->
                val tier = p.get(JSON_KEY_PRICE_TIER)
                val message = p.get(JSON_KEY_PRICE_MESSAGE)
                val currency = p.get(JSON_KEY_PRICE_CURRENCY)

                message.takeIf { it != null }.apply {
                    priceFormatted.append(this?.asString)
                    priceFormatted.append(" ")
                }

                currency.takeIf { it != null }.apply {
                    try {
                        val tierInt = Integer.parseInt(tier?.asString)
                        for (i in 0..tierInt) {
                            priceFormatted.append(this?.asString)
                        }
                    } catch (ex : NumberFormatException) {
                        Log.e(LOG_TAG, ex.localizedMessage)
                    }
                }
            } ?: run {
                priceFormatted.append("-")
            }

            // Rating
            var rating : Int?
            val ratingJson = venue.asJsonObject.get(JSON_KEY_RATING)
            ratingJson.takeIf { it != null }.apply {
                rating = this?.asInt
            }

            // Photo
            val photo = venue.asJsonObject
                .getAsJsonObject(JSON_KEY_PHOTOS)
            val photoUrl = StringBuilder()
            photoUrl.append(photo.asJsonObject.get(JSON_KEY_VENUE_CATEGORY_ICON_PREFIX).asString)
            photoUrl.append(PHOTO_SIZE)
            photoUrl.append(photo.asJsonObject.get(JSON_KEY_VENUE_CATEGORY_ICON_SUFFIX).asString)

            venueDetails = VenueDetails(
                name,
                phone,
                formatedAddress.toString(),
                status ?: "",
                categories.toString(),
                priceFormatted.toString(),
                rating ?: 0,
                photoUrl.toString()
            )

        } catch (e: JSONException) {
            Log.e(LOG_TAG, e.localizedMessage)
        } finally {
            return venueDetails
        }
    }
}