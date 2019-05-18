package com.vcamargo.myplaces.converter

import retrofit2.Converter
import okhttp3.ResponseBody
import retrofit2.Retrofit
import java.lang.reflect.Type

class VenuesConverterFactory : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        annotations.forEach {
            if (it.toString().contains("venues/search?intent=browse")) {
                return VenuesSearchConverter()
            }
        }
        return VenueDetailsConverter()
    }
}