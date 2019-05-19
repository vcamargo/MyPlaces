package com.vcamargo.myplaces.converter

import android.util.Log
import com.vcamargo.myplaces.converter.VenuesSearchConverter.Companion.JSON_KEY_VENUE_CATEGORY_ICON_PREFIX
import com.vcamargo.myplaces.converter.VenuesSearchConverter.Companion.JSON_KEY_VENUE_CATEGORY_ICON_SUFFIX
import com.vcamargo.myplaces.converter.VenuesSearchConverter.Companion.JSON_KEY_VENUE_NAME
import com.vcamargo.myplaces.model.VenueDetails
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.IOException
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
        const val NO_VALUE = "-"
    }
    override fun convert(responseBody: ResponseBody): VenueDetails? {
        var venueDetails : VenueDetails? = null
        try {
            var name : String? = null
            var status : String? = null
            var phone : String? = null
            var address : String? = null
            var categories : String? = null
            var price : String? = null
            var rating : Int? = 0
            var photo : String? = null

            val res = responseBody.string()

            val jsonParser = JsonParser()
            val response = jsonParser.parse(res).asJsonObject
                .getAsJsonObject(VenuesSearchConverter.JSON_KEY_RESPONSE)

            response?.let {r ->
                val venue = r.asJsonObject
                .getAsJsonObject(JSON_KEY_VENUE)

                venue?.let {v ->
                    // Name
                    name = v.asJsonObject.get(JSON_KEY_VENUE_NAME).asString

                    // Phone
                    val contact = v.asJsonObject
                        .getAsJsonObject(JSON_KEY_VENUE_CONTACT)
                    contact.takeIf { it != null }.apply {
                        phone = this?.asJsonObject?.get(JSON_KEY_VENUE_CONTACT_PHONE)?.asString
                    }

                    // Address
                    val location = venue.asJsonObject
                        .getAsJsonObject(VenuesSearchConverter.JSON_KEY_VENUE_LOCATION)
                    location.takeIf { it != null }.apply {
                        val formatedAddressArr = this?.asJsonObject?.getAsJsonArray(JSON_KEY_FORMATTED_ADDRESS)
                        val formatedAddress = StringBuilder()
                        formatedAddressArr?.forEach {
                            formatedAddress.append(it.asString)
                            formatedAddress.append("\n")
                        }
                        address = formatedAddress.toString()
                    }


                    // Hours
                    val hours = v.asJsonObject
                        .getAsJsonObject(JSON_KEY_HOURS)
                    hours.takeIf { it != null }.apply {
                        status = this?.asJsonObject?.get(JSON_KEY_STATUS)?.asString
                    }

                    // Categories
                    val categoriesArr = v.asJsonObject
                        .getAsJsonArray(VenuesSearchConverter.JSON_KEY_VENUE_CATEGORIES)
                    categoriesArr?.takeIf { it != null }.apply {
                        val cat = StringBuilder()
                        categoriesArr.forEach {
                            cat.append(it.asJsonObject.get(JSON_KEY_VENUE_NAME).asString + " ")
                        }

                        categories = cat.toString()
                    }

                    // Price
                    val venuePrice = v.asJsonObject
                        .getAsJsonObject(JSON_KEY_PRICE)

                    venuePrice?.let {p ->
                        var priceFormatted = StringBuilder()
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
                                priceFormatted.append(NO_VALUE)
                            }
                        }
                        price = priceFormatted.toString()
                    }

                    // Rating
                    val ratingJson = v.asJsonObject.get(JSON_KEY_RATING)
                    ratingJson.takeIf { it != null }.apply {
                        rating = this?.asInt
                    }

                    // Photo
                    val vanuePhoto = v.asJsonObject
                        .getAsJsonObject(JSON_KEY_PHOTOS)
                    vanuePhoto.takeIf { it != null }.apply {
                        val photoUrl = StringBuilder()
                        photoUrl.append(this?.asJsonObject?.get(JSON_KEY_VENUE_CATEGORY_ICON_PREFIX)?.asString)
                        photoUrl.append(PHOTO_SIZE)
                        photoUrl.append(this?.asJsonObject?.get(JSON_KEY_VENUE_CATEGORY_ICON_SUFFIX)?.asString)

                        photo = photoUrl.toString()
                    }
                }
            }


            venueDetails = VenueDetails(
                name ?: NO_VALUE,
                phone ?: NO_VALUE,
                address ?: NO_VALUE,
                status ?: NO_VALUE,
                categories ?: NO_VALUE,
                price ?: NO_VALUE,
                rating ?: 0,
                photo ?: NO_VALUE
            )

        } catch (e : JsonSyntaxException) {
            Log.e(LOG_TAG, e.localizedMessage)
        } catch (e: IOException) {
            Log.e(LOG_TAG, e.localizedMessage)
        }
        finally {
            return venueDetails
        }
    }
}